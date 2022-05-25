package shapes;

import java.awt.Graphics2D;
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
	private int px, py;
	private double cx, cy;
	private double xScale, yScale;
	
	private AffineTransform affineTransform;
	
	private boolean bSelected;

	
	// setters and getters
	public EAnchors getSelectedAnchor() {
		return this.anchors.geteSelectedAnchor();
	}
//	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
//		this.anchors.seteSelectedAnchor(eSelectedAnchor);
//	}
	public boolean isSelected() {
		return bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	// constructors
	public TShape() {
		this.bSelected = false;
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		
		this.anchors = new TAnchors();
	}
	public abstract TShape clone();	
	
	// methods
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}

	public boolean contains(int x, int y) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if (this.bSelected) {
			if (this.anchors.contains(x, y)) {
				return true;
			}
		}
		if(transformedShape.contains(x, y)) {
			this.anchors.seteSelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		graphics2D.draw(transformedShape);
		if (this.bSelected) {
			this.anchors.draw(graphics2D, transformedShape.getBounds());			
		}
	}
	public void prepareMoving(int x, int y) {
		this.px = x;
		this.py = y;		
	}
	public void keepMoving(int x, int y) {
		this.affineTransform.translate(x-this.px, y-this.py);
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
		Point2D reiszeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y);
		this.cx = reiszeAnchorPoint.getX();
		this.cy = reiszeAnchorPoint.getY();
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
	private void getResizeScale(int x, int y) {
		EAnchors eReiszeanchor = this.anchors.geteResizeAnchor();
		double w1 = px-cx;
		double w2 = x-cx;
		
		double h1 = py-cy;
		double h2 = y-cy;
		
		switch (eReiszeanchor) {
		case eNW: xScale = w2/w1;	yScale = h2/h1;	break;
		case eWW: xScale = w2/w1; 	yScale = 1;		break;
		case eSW: xScale = w2/w1; 	yScale = h2/h1;	break;
		case eSS: xScale = 1; 		yScale = h2/h1;	break;
		case eSE: xScale = w2/w1;   yScale = h2/h1;	break;
		case eEE: xScale = w2/w1; 	yScale = 1;		break;
		case eNE: xScale = w2/w1;   yScale = h2/h1;	break;
		case eNN: xScale = 1; 		yScale = h2/h1;	break;
		default:									break;
		}
	}

}

