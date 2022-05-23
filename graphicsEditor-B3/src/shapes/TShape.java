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

	private AffineTransform affineTransform;
	
	private boolean bSelected;

	
	// setters and getters
	public EAnchors getSelectedAnchor() {
		return this.anchors.geteSelectedAnchor();
	}
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
		if (this.bSelected) {
			if (this.anchors.contains(x, y, this.shape.getBounds(), this.affineTransform)) {
				return true;
			}
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
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
			this.anchors.draw(graphics2D, this.shape.getBounds(), this.affineTransform);			
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
		this.anchors.setResizeAnchor();		
		this.px = x;
		this.py = y;
	}
	
	public void keepResizing(int x, int y) {
		Point2D origin = this.anchors.getResizeAnchorPosition();
		double cx = origin.getX();
		double cy = origin.getY();
		
		Point2D scale = this.anchors.getResizeScale(x, y, px, py, this.shape, this.affineTransform);
		double sx = scale.getX();
		double sy = scale.getY();
		
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(sx, sy);
		this.affineTransform.translate(-cx, -cy);

		this.px = x;
		this.py = y;	
	}
	
	public void finalizeResizing() {
		this.shape = this.affineTransform.createTransformedShape(this.shape);
		this.affineTransform.setToIdentity();
	}
}

