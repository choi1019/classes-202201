package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	// attributes
	private static final long serialVersionUID = 1L;
	// components
	protected Shape shape;
	private TAnchors anchors;
	// working
	private boolean bSelected;
	private EAnchors eSelectedAnchor;
	
	// constructor
	public TShape() {
		this.anchors = new TAnchors();
		this.bSelected = false;
	}
	
	public abstract TShape clone();
	public void initialize() {}
	
	// setters and getters
	public abstract void setOrigin(int x, int y);
	public void addPoint(int x, int y) {}	
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
		if(this.shape.contains(x, y)) {
			this.eSelectedAnchor = EAnchors.eMove;
			return true;
		}
		return false;
	}	
	public void draw(Graphics2D graphics2D) {
		graphics2D.draw(this.shape);
		if (isSelected()) {
			this.anchors.draw(graphics2D, this.shape.getBounds());
		}
	}
	
	public abstract void resize(int x, int y);	
}

