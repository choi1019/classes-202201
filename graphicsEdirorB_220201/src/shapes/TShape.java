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
	
	// setters and getters
	public EAnchors getSelectedAnchor() {
		return eSelectedAnchor;
	}
	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}
	public boolean isSelected() {
		return bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	// constructors
	public TShape() {
		this.anchors = new TAnchors();
	}
	public abstract TShape clone();	
	
	// methods
	public abstract void setOrigin(int x, int y);
	public abstract void resize(int x, int y);
	public void addPoint(int x, int y) {}

	public boolean contains(int x, int y) {
		if (this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x, y);
			if (this.eSelectedAnchor != null) {
				return true;
			}
		}
		if(this.shape.contains(x, y)) {
			this.eSelectedAnchor = EAnchors.eMove;
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2D) {
		graphics2D.draw(this.shape);
		if (this.bSelected) {
			this.anchors.draw(graphics2D, this.shape.getBounds());			
		}
	}
}

