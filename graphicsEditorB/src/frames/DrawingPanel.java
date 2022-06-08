package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import shapes.TAnchors.EAnchors;
import shapes.TSelection;
import shapes.TShape;
import transformer.Drawer;
import transformer.Mover;
import transformer.Resizer;
import transformer.Rotator;
import transformer.Transformer;

public class DrawingPanel extends JPanel { 

	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<TShape> shapes;
	private BufferedImage bufferedImage;
	private Graphics2D graphics2DBufferedImage;
	
	// associated attribute
	private ETools selectedTool;
	private TShape selectedShape;
	private TShape currentShape;
	private Transformer transformer;
	
	// working variables
	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing, eMoving, eRotating
	}
	EDrawingState eDrawingState;
	
	public DrawingPanel() {
		this.setBackground(Color.WHITE);		
		this.eDrawingState = EDrawingState.eIdle;
		
		this.shapes = new Vector<TShape>();
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
	}
	
	public void initialize() {
		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
	}
	
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
	}
	public Object getShapes() {
		return this.shapes;		
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;		
	}

	// overriding
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		this.graphics2DBufferedImage.clearRect(0, 0, 
				this.bufferedImage.getWidth(), this.bufferedImage.getHeight());
		for (TShape shape:this.shapes) {
			shape.draw(this.graphics2DBufferedImage);
		}		
		graphics.drawImage(this.bufferedImage, 0, 0, this);
	}	
	
	private void prepareTransforming(int x, int y) {
		if (selectedTool == ETools.eSelection) {
			currentShape = onShape(x, y);
			if (currentShape != null) {	
				EAnchors eAnchor = currentShape.getSelectedAnchor();
				if(eAnchor == EAnchors.eMove) {
					this.transformer = new Mover(this.currentShape);
				} else if(eAnchor == EAnchors.eRR) {
					this.transformer = new Rotator(this.currentShape);
				} else {							
					this.transformer = new Resizer(this.currentShape);
				}
			} else {
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(this.currentShape);
			}
		} else {
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(this.currentShape);
		}
		
		this.transformer.prepare(x, y);
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
	}
	
	private void keepTransforming(int x, int y) {
		// erase
		this.currentShape.draw(this.graphics2DBufferedImage);
		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
		// transform
		this.transformer.keepTransforming(x, y);
		// draw
		this.currentShape.draw(this.graphics2DBufferedImage);
		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
	}
	
	private void continueTransforming(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	
	private void finishTransforming(int x, int y) {
		this.graphics2DBufferedImage.setPaintMode();
		this.transformer.finalize(x, y);
		
		if (this.selectedShape!=null) {
			this.selectedShape.setSelected(false);
		}
		
		if (!(this.currentShape instanceof TSelection)) {
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape;
			this.selectedShape.setSelected(true);
		}
		
		this.repaint();
	}	

	private TShape onShape(int x, int y) {
		for (TShape shape: this.shapes ) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}
	private void changeSelection(int x, int y) {
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		this.repaint();
		// draw anchors
		this.selectedShape = this.onShape(x, y);
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(true);
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		if (this.selectedTool == ETools.eSelection) {
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			
			this.currentShape = this.onShape(x, y);		
			if (this.currentShape != null) {
				cursor = new Cursor(Cursor.MOVE_CURSOR);
				if (this.currentShape.isSelected()) {
					EAnchors eAnchor = this.currentShape.getSelectedAnchor();
					switch(eAnchor) {
						case eRR: cursor = new Cursor(Cursor.HAND_CURSOR); break;
						case eNW: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
						case eWW: cursor = new Cursor(Cursor.W_RESIZE_CURSOR); break;
						case eSW: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
						case eSS: cursor = new Cursor(Cursor.S_RESIZE_CURSOR); break;
						case eSE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
						case eEE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR); break;
						case eNE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
						case eNN: cursor = new Cursor(Cursor.N_RESIZE_CURSOR); break;
						default: break;
					}
					
				}
			}
		}
		this.setCursor(cursor);
	}
	
	private class MouseHandler implements MouseInputListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.lButtonClicked(e);
				} else if (e.getClickCount() == 2) {
					this.lButtonDoubleClicked(e);
				}
			}
		}
		
		private void lButtonClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				changeSelection(e.getX(), e.getY());
				if (selectedTool.getTransformationStyle() == ETransformationStyle.eNPTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;
				}
			} else if (eDrawingState == EDrawingState.eNPointDrawing) {
				continueTransforming(e.getX(), e.getY());
			}
		}
		private void lButtonDoubleClicked(MouseEvent e) {			
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}

		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getTransformationStyle() == ETransformationStyle.e2PTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			} 
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}
	
	}
}
