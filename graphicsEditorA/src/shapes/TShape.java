package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
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
	private boolean bSelected;
	private EAnchors eSelectedAnchor;
	
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
		return this.eSelectedAnchor;
	}
	
	// methods
	public boolean contains(int x, int y) {
		if (isSelected()) {
			EAnchors eAnchor = this.anchors.contains(x, y);
			if (eAnchor != null) {
				this.eSelectedAnchor = eAnchor;
				return true;
			}
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if(transformedShape.contains(x, y)) {
			this.eSelectedAnchor = EAnchors.eMove;
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
}

