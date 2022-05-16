package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constants.EDrawingStyle;
import global.Constants.ETools;
import shapes.TAnchors.EAnchors;
import shapes.TSelection;
import shapes.TShape;
import transformer.Drawer;
import transformer.Resizer;
import transformer.Rotator;
import transformer.Transformer;
import transformer.Translator;

public class DrawingPanel extends JPanel {

	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<TShape> shapes;
	
	// associated attribute
	private ETools selectedTool;
	private TShape selectedShape;
	private TShape currentShape;
	
	// working variables
	private Transformer transformer;
	
	private enum EDrawingState {
		eIdle,
		e2PointTransforming,
		eNPointTransforming
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
		for (TShape shape:this.shapes) {
			shape.draw((Graphics2D)graphics);
		}
	}	

	private void selectTransformer(int x, int y) {
		if (this.selectedTool == ETools.eSelection) {
			// selection
			this.currentShape = onShape(x, y);
			if (this.currentShape != null) {
				EAnchors eAnchor = currentShape.getSelectedAnchor();
				if(eAnchor == EAnchors.eMove) {
					this.transformer = new Translator(currentShape);
				} else if(eAnchor == EAnchors.eRR) {
					this.transformer = new Rotator(currentShape);
				} else {							
					this.transformer = new Resizer(currentShape);
				}
			} else {
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(currentShape);
			}
		} else {
			// drawing
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(currentShape);
		}	
	}
	
	private void initTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.transformer.prepare(x, y, graphics2D);
	}
	
	private void keepTransforming(int x, int y) {
		// erase
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.currentShape.draw(graphics2D);
		// draw
		this.transformer.keepTransforming(x, y, graphics2D);
		this.currentShape.draw(graphics2D);
	}
	
	private void continueTransforming(int x, int y) {
		this.transformer.addPoint(x, y);
	}
	
	private void finishTransforming(int x, int y) {
		if (this.selectedShape!=null) {
			this.selectedShape.setSelected(false);
		}
		this.selectedShape = this.currentShape;
		this.selectedShape.setSelected(true);
		
		if (!(this.selectedShape instanceof TSelection)) {
			this.shapes.add(this.currentShape);
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
	
//	private void changeSelection(int x, int y) {
//		if (this.selectedShape != null) {
//			this.selectedShape.setSelected(false);
//		}
//		this.repaint();
//		// draw anchors
//		this.selectedShape = this.onShape(x, y);
//		if (this.selectedShape != null) {
//			this.selectedShape.setSelected(true);
//			this.selectedShape.draw((Graphics2D) this.getGraphics());
//		}
//	}
	
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
				if (selectedTool.getDrawingStyle() == EDrawingStyle.eNPoint) {
					selectTransformer(e.getX(), e.getY());
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointTransforming;
				}
			} else if (eDrawingState == EDrawingState.eNPointTransforming) {
				continueTransforming(e.getX(), e.getY());
			}
		}
		private void lButtonDoubleClicked(MouseEvent e) {			
			if (eDrawingState == EDrawingState.eNPointTransforming) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointTransforming) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getDrawingStyle() == EDrawingStyle.e2Point) {
					selectTransformer(e.getX(), e.getY());
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointTransforming;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransforming) {
				keepTransforming(e.getX(), e.getY());
			} 
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransforming) {
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
