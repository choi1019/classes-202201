package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	// attributes
	private static final long serialVersionUID = 1L;
	// components
	protected Shape shape;
	private TAnchors anchors;
	// working
	private AffineTransform affineTransform;
	private int px, py;
	private double cx, cy;
	private double xScale, yScale;
	private boolean bSelected;
	
	// constructor
	public TShape() {
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		
		this.anchors = new TAnchors();
		this.bSelected = false;
	}
	
	public abstract TShape clone();
	public void initialize() {}
	
	// setters and getters
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelectd(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public EAnchors getSelectedAnchor() {
		return this.anchors.getSelectedAnchor();
	}
	
	// methods
	public boolean contains(int x, int y) {
		if (isSelected()) {
			if (this.anchors.contains(x, y)) {
				return true;
			}
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if(transformedShape.contains(x, y)) {
			this.anchors.setSelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}	
	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		graphics2D.draw(transformedShape);
		
		if (isSelected()) {
			this.anchors.draw(graphics2D, transformedShape.getBounds());
		}
	}
	
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}	

	public void prepareMoving(int x, int y) {
		this.px = x;
		this.py = y;
	}	
	public void keepMoving(int x, int y) {
		this.affineTransform.translate(x - this.px, y - this.py);
		this.px = x;
		this.py = y;
	}
	public void finalizeMoving(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape);
		this.affineTransform.setToIdentity();
	}
	public void prepareResizing(int x, int y) {
		this.px = x;
		this.py = y;
		Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y);
		this.cx = resizeAnchorPoint.getX();
		this.cy = resizeAnchorPoint.getY();
	}	
	public void keepResizing(int x, int y) {
		this.getResizeScale(x, y);
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(this.xScale, this.yScale);
		this.affineTransform.translate(-cx, -cy);
		this.px = x;
		this.py = y;
	}
	public void finalizeResizing(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape);
		this.affineTransform.setToIdentity();
	}
	protected void getResizeScale(int x, int y) {
		EAnchors eReiszeAnchor = this.anchors.getResizeAnchor();
		double w1 = px - cx;
		double w2 = x - cx;
		double h1 = py - cy;
		double h2 = y - cy;
		
		switch (eReiszeAnchor) {
			case eNW: xScale = w2/w1; 	yScale = h2/h1; 	break;
			case eWW: xScale = w2/w1; 	yScale = 1; 		break;				
			case eSW: xScale = w2/w1; 	yScale = h2/h1; 	break;				
			case eSS: xScale = 1; 		yScale = h2/h1; 	break;				
			case eSE: xScale = w2/w1; 	yScale = h2/h1; 	break;				
			case eEE: xScale = w2/w1; 	yScale = 1; 		break;				
			case eNE: xScale = w2/w1; 	yScale = h2/h1; 	break;				
			case eNN: xScale = 1; 		yScale = h2/h1; 	break;				
			default: break;
		}

	}


}

