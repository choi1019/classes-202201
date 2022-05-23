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

import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import shapes.TAnchors.EAnchors;
import shapes.TSelection;
import shapes.TShape;
import transformers.Drawer;
import transformers.Mover;
import transformers.Resizer;
import transformers.Rotator;
import transformers.Transformer;

public class DrawingPanel extends JPanel {

	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<TShape> shapes;
	
	// associated attribute
	private TShape selectedShape;
	private ETools selectedTool;
	private TShape currentShape;
	private Transformer transformer;
	
	// working variables
	private enum EDrawingState {
		eIdle,
		e2PointTransformation,
		eNPointTransformation
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
	
	// file open/save
	public Object getShapes() {	
		return this.shapes;	
	}
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) { 
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
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
	
	private void prepareTrnasformation(int x, int y) {
		if (selectedTool == ETools.eSelection) {
			currentShape = onShape(x, y);
			if (currentShape != null) {
				if (currentShape.getSelectedAnchor() == EAnchors.eMove) {
					this.transformer = new Mover(currentShape);
				} else if (currentShape.getSelectedAnchor() == EAnchors.eRR) {
					this.transformer = new Rotator(currentShape);
				} else {
					this.transformer = new Resizer(currentShape);
				}
			} else {
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(currentShape);
			}
		} else {
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(currentShape);
		}
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.transformer.prepare(x, y, graphics2D);
	}
	
	private void keepTrnasformation(int x, int y) {
		// erase
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.currentShape.draw(graphics2D);
		// draw
		this.transformer.keepTransforming(x, y, graphics2D);
		this.currentShape.draw(graphics2D);
	}
	
	private void continueTrnasformation(int x, int y) {
		this.currentShape.addPoint(x, y);
	}
	
	private void finishTrnasformation(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.transformer.finalize(x, y, graphics2D);
		
		if (this.selectedShape!=null) {
			this.selectedShape.setSelectd(false);
		}

		if (!(this.currentShape instanceof TSelection)) { 
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape;
			this.selectedShape.setSelectd(true);
		}		
		
		this.repaint();
	}	

	private TShape onShape(int x, int y) {
		for (TShape shape: this.shapes) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}
	
	private void changeSelection(int x, int y) {
		// erase previous selection
		if (this.selectedShape!= null) {
			this.selectedShape.setSelectd(false);
		}
		// draw new selection
		this.selectedShape = this.onShape(x, y);
		if (this.selectedShape!= null) {
			this.selectedShape.setSelectd(true);
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
		this.repaint();
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		if (this.selectedTool != ETools.eSelection) {
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		}		
		this.currentShape = onShape(x, y);
		if (this.currentShape != null) {
			cursor = new Cursor(Cursor.MOVE_CURSOR);
			if (this.currentShape.isSelected()) {
				EAnchors eAnchor = this.currentShape.getSelectedAnchor();
				switch (eAnchor) {
					case eRR: cursor = new Cursor(Cursor.HAND_CURSOR); 		break;
					case eNW: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
					case eWW: cursor = new Cursor(Cursor.W_RESIZE_CURSOR); 	break;
					case eSW: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
					case eSS: cursor = new Cursor(Cursor.S_RESIZE_CURSOR); 	break;
					case eSE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
					case eEE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR); 	break;
					case eNE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
					case eNN: cursor = new Cursor(Cursor.N_RESIZE_CURSOR); 	break;
					default: break;
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
				if (selectedTool.getETransformationStyle() == ETransformationStyle.eNPoint) {
					prepareTrnasformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointTransformation;
				}
			} else if (eDrawingState == EDrawingState.eNPointTransformation) {
				continueTrnasformation(e.getX(), e.getY());
			}
		}
		private void lButtonDoubleClicked(MouseEvent e) {			
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				finishTrnasformation(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				keepTrnasformation(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}		

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getETransformationStyle() == ETransformationStyle.e2Point) {
					prepareTrnasformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointTransformation;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransformation) {
				keepTrnasformation(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransformation) {
				finishTrnasformation(e.getX(), e.getY());
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
