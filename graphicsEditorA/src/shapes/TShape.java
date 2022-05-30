package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
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
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public AffineTransform getAffineTransform() {
		return affineTransform;
	}
	public TAnchors getAnchors() {
		return anchors;
	}
	public EAnchors getSelectedAnchor() {
		return this.anchors.getSelectedAnchor();
	}
	
	// constructor
	public TShape() {
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		
		this.anchors = new TAnchors();
		this.bSelected = false;
	}	
	public abstract TShape clone();
	public void initialize() {}
	
	// methods
	public boolean contains(int x, int y) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if (isSelected()) {
			if (this.anchors.contains(x, y)) {
				return true;
			}
		}
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
}

