package frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.GClipboard;
import global.GConstants.EAnchors;
import shape.GGroup;
import shape.GPolygon;
import shape.GSelectContainer;
import shape.GSelect;
import shape.GShapeManager;
import shape.GTextArea;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GCreator;
import transformer.GTransformer;

public class GDrawingPanel extends JPanel implements Printable {
	// declarations
	private enum EDrawingState {eIdle, eTPTransforming, eNPTransforming};
	
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private Vector<GShapeManager> shapeVector;
	private GClipboard clipboard;
	
	// working variables
	private GTransformer transformer;
	private GShapeManager selectedTool;
	private GShapeManager selectedShape;
	private GShapeManager clippedShape;

	private boolean updated;
	private int startX, startY;
	
	// getters & setters
	public Object getShapeVector() { return this.shapeVector; }	
	@SuppressWarnings("unchecked")
	public void setShapeVector(Object shapeVector) { this.shapeVector = (Vector<GShapeManager>)shapeVector; }
	
	public void setSelectedTool(GShapeManager selectedTool) { this.selectedTool = selectedTool; }
	
	public boolean isUpdated() { return this.updated; }
	public void setUpdated(boolean updated) { this.updated = updated; }
	
	private void setSelectedShape(GShapeManager selectedShape) {
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		this.selectedShape = selectedShape;
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(true);
		}
	}
	
	// constructors
	public GDrawingPanel() {
		super();
		// initialize components
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		
		this.shapeVector = new Vector<GShapeManager>();
		this.clipboard = new GClipboard();
		
		this.selectedTool = null;
	}
	
	public void initialize() {
		this.setBackground(Color.WHITE);
		
		this.shapeVector.clear();
		this.clipboard.initialize(this.shapeVector);

		this.transformer = null;
		this.selectedShape = null;
		this.clippedShape = null;
		
		this.updated = false;
		
		this.repaint();
	}
	
	private void clearPanel(Graphics2D g2D) {
		Color foreGround = g2D.getColor();
		g2D.setColor(this.getBackground());
		g2D.fill(this.getVisibleRect());
		g2D.setColor(foreGround);	
	}
	
	private void drawShapes(Graphics2D g2D) {
		this.clearPanel(g2D);
		for (GShapeManager shape: this.shapeVector) {
			shape.draw(g2D);		}
	
	}
	private void drawAll(Graphics2D g2D) {
		this.clearPanel(g2D);
		this.drawShapes(g2D);
		if (this.selectedShape != null) {
			this.selectedShape.drawAnchors(g2D);
		}
	}
	public void paint(Graphics graphics) {
		super.paint(graphics);		
		this.drawAll((Graphics2D)graphics);
	}
	
	
	private GShapeManager onShape(int x, int y) {
		if (this.selectedShape != null) {
			if (this.selectedShape.contains(x, y)) {
				return this.selectedShape;
			}
		}
		
		for (int i=this.shapeVector.size()-1; i>=0; i--) {
			if (this.shapeVector.get(i).contains(x, y)) {
				return this.shapeVector.get(i);
			}
		}
		return null;
	}	
	
	private void selectAction(int x, int y) {
		GShapeManager newShape = null;
		this.transformer = null;
		if (this.selectedTool instanceof GSelect) {
			newShape = this.onShape(x, y);
			if (newShape == null) {
				newShape = this.selectedTool.newInstance();
				this.transformer = new GCreator(newShape);				
			} else {
				EAnchors eAnchor = newShape.getSelectedAnchor();
				if (eAnchor == EAnchors.M) {
					this.transformer = new GMover(newShape);
				} else if (eAnchor == EAnchors.R) {
					this.transformer = new GRotator(newShape);
				} else {
					this.transformer = new GResizer(newShape);
				}
			}
			
		} else {
			newShape = this.selectedTool.newInstance();
			this.transformer = new GCreator(newShape);
		}
		
		this.setSelectedShape(newShape);
	}
	
	private void initTransforming(int x, int y) {		
		Graphics2D g2D = (Graphics2D)this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.selectedShape.drawAnchors(g2D);
		g2D.setPaintMode();
		this.drawShapes(g2D);
		
		this.transformer.initTransforming(g2D, x, y);
		
		this.startX = x;
		this.startY = y;
	}
	
	private void keepTransforming(int x, int y) {
		if (this.transformer instanceof GCreator) {
			Graphics2D g2D = (Graphics2D) this.getGraphics();
			g2D.setXORMode(this.getBackground());
			this.selectedShape.draw(g2D);
			this.transformer.keepTransforming(g2D, x, y);
			this.selectedShape.draw(g2D);
		} else {
			Graphics2D g2D = (Graphics2D) this.getGraphics();
			this.clearPanel(g2D);
			this.transformer.keepTransforming(g2D, x, y);
			this.drawShapes(g2D);
		}
	}
	
	private void  continueTransforming(int x, int y) {
		if (this.transformer instanceof GCreator) {
			Graphics2D g2D = (Graphics2D) this.getGraphics();
			this.transformer.continueTransforming(g2D, x, y);			
		}	
	}
	
	private void finishTransforming(int x, int y) {
		Graphics2D g2D = (Graphics2D)this.getGraphics();
		this.transformer.finishTransforming(g2D, x, y);

		if (this.startX == x && this.startY == y) {
			if (this.transformer instanceof GCreator) {
				this.setSelectedShape(null);
			}
		} else {
			if (this.selectedShape instanceof GSelect) {
				GSelectContainer selectContainer = new GSelectContainer();
				for (GShapeManager shapeManager : this.shapeVector) {
					if (this.selectedShape.contains(shapeManager)) {
						selectContainer.add(shapeManager);
					}
				}
				this.setSelectedShape(selectContainer);
			} else if (this.transformer instanceof GCreator) {
				if (this.selectedShape instanceof GTextArea) {
					this.add(((GTextArea)this.selectedShape).getTextArea());
				} else {
					this.shapeVector.add(this.selectedShape);
				}
			}
			this.updated = true;
			this.clipboard.push(this.shapeVector);
		}
		this.drawAll(g2D);
	}
	
	public void setLineColor(Color lineColor) {
		if (this.selectedShape != null) {
			this.selectedShape.setLineColor(lineColor);
			Graphics2D g2D = (Graphics2D)this.getGraphics();
			this.selectedShape.draw(g2D);
			this.selectedShape.drawAnchors(g2D);;
		}
	}
	
	public void setFillColor(Color fillColor) {
		if (this.selectedShape != null) {
			this.selectedShape.setFillColor(fillColor);
			Graphics2D g2D = (Graphics2D)this.getGraphics();
			this.selectedShape.draw(g2D);
			this.selectedShape.drawAnchors(g2D);;
		}
	}
	
	public void group() {
		if (this.selectedShape instanceof GSelectContainer) {
			GSelectContainer preGroup = (GSelectContainer)this.selectedShape;
			
			for (GShapeManager shape: preGroup.getShapes()) {
				this.shapeVector.remove(shape);
			}
			GGroup group = new GGroup();
			for (GShapeManager shape: preGroup.getShapes()) {
				group.add(shape);
			}			
			this.shapeVector.add(group);			
			this.setSelectedShape(group);
			
			this.repaint();
		}		
	}
	public void unGroup() {
		if (this.selectedShape instanceof GGroup) {	
			GGroup group = (GGroup)this.selectedShape;
			
			GSelectContainer preGroup = new GSelectContainer();
			for (GShapeManager shape: group.getShapes()) {
				this.shapeVector.add(shape);
				preGroup.add(shape);
			}
			this.shapeVector.remove(group);
			
			this.setSelectedShape(preGroup);
			this.repaint();
		}
	}

	public void cut() {
		if (this.selectedShape != null) {
			this.clippedShape = this.selectedShape;
			
			if (this.selectedShape instanceof GSelectContainer) {
				for (GShapeManager shape: ((GSelectContainer) this.selectedShape).getShapes()) {
					this.shapeVector.remove(shape);
				}
			} else {
				this.shapeVector.remove(this.selectedShape);
			}
			this.setSelectedShape(null);
			this.repaint();
		}
	}

	public void copy() {
		if (this.selectedShape != null) {
			this.clippedShape = this.selectedShape;
		}
	}

	public void paste() {
		if (this.clippedShape != null) {
			GShapeManager newShape = this.clippedShape.clone();
			newShape.moveALittle();
			if (newShape instanceof GSelectContainer) {
				for (GShapeManager shape: ((GSelectContainer) newShape).getShapes()) {
					this.shapeVector.add(shape);
				}
			} else {
				this.shapeVector.add(newShape);
			}
			this.setSelectedShape(newShape);
			this.repaint();
			this.clippedShape = newShape;
		}
	}

	public void delete() {
		if (this.selectedShape != null) {
			if (this.selectedShape instanceof GSelectContainer) {
				for (GShapeManager shape: ((GSelectContainer) this.selectedShape).getShapes()) {
					this.shapeVector.remove(shape);
				}
			} else {
				this.shapeVector.remove(this.selectedShape);
			}
			this.setSelectedShape(null);
			this.repaint();
		}
	}
	
	public void undo() {
		Vector<GShapeManager> element = this.clipboard.undo();
		if (element != null) {
			this.shapeVector = element;
			this.selectedShape = null;
			for (GShapeManager shapeManager: this.shapeVector) {
				if (shapeManager.isSelected()) {
					this.selectedShape = shapeManager;
				}
			}
			this.drawAll((Graphics2D)this.getGraphics());
		}
	}
	
	public void redo() {
		Vector<GShapeManager> element = this.clipboard.redo();
			if (element != null) {
			this.shapeVector = element;
			this.selectedShape = null;
			for (GShapeManager shapeManager: this.shapeVector) {
				if (shapeManager.isSelected()) {
					this.selectedShape = shapeManager;
				}
			}
			this.drawAll((Graphics2D)this.getGraphics());
		}
	}	
	
	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) { /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
		}
		/*
		 * User (0,0) is typically outside the imageable area, so we must translate by
		 * the X and Y values in the PageFormat to avoid clipping
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		/* Now we perform our rendering */
		for (GShapeManager shape: this.shapeVector) {
			shape.draw((Graphics2D)g);
		}

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}

	private void changeCursor(int x, int y) {
		GShapeManager shape = this.onShape(x, y);
		if (shape == null) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			EAnchors eAnchor = shape.getSelectedAnchor();
			if (eAnchor != null) {
				this.setCursor(eAnchor.getCursor());
			}
		}
	}
	
	private class MouseHandler implements MouseInputListener {
		private EDrawingState eDrawingState;
		
		public MouseHandler() {
			this.eDrawingState = EDrawingState.eIdle;	
		}
		
		private void singleClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if ((selectedShape instanceof GPolygon) && (transformer instanceof GCreator)) {
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPTransforming;
				}
			} else if (eDrawingState == EDrawingState.eNPTransforming) {
				continueTransforming(e.getX(), e.getY());
			}
		}
		
		private void doubleClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPTransforming) {
				if (selectedShape instanceof GPolygon) {
					finishTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			}			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				this.singleClicked(e);
			} else if (e.getClickCount() == 2) {
				this.doubleClicked(e);
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				selectAction(e.getX(), e.getY());
				if (!((selectedShape instanceof GPolygon) && (transformer instanceof GCreator))) {
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eTPTransforming;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			 if (eDrawingState == EDrawingState.eTPTransforming) {
				keepTransforming(e.getX(), e.getY());
			} 		
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTPTransforming) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}  
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eNPTransforming) {
				keepTransforming(e.getX(), e.getY());			
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}
}
