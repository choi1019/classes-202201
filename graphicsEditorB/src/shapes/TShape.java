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
	private boolean bSelected;
	
	// components
	protected Shape shape;

	private AffineTransform affineTransform;
	private TAnchors anchors;
	
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
	public AffineTransform getAffineTransform() {
		return this.affineTransform;
	}
	public TAnchors getAnchors() {
		return this.anchors;
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
}

