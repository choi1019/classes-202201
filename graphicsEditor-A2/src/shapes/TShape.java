package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	// attributes
	private static final long serialVersionUID = 1L;
	// components
	protected Shape shape;
	private TAnchors anchors;
	// working
	int px, py;
	protected AffineTransform affineTransform;
	private boolean bSelected;
	private EAnchors eSelectedAnchor;
	
	// constructor
	public TShape() {
		this.bSelected = false;
		this.anchors = new TAnchors();
		
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
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
		try {
			Point point = new Point(x, y);
			this.affineTransform.inverseTransform(point, point);
			x = point.x;
			y = point.y;		
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		graphics2D.setTransform(this.affineTransform);
		
		graphics2D.draw(this.shape);
		if (isSelected()) {
			this.anchors.draw(graphics2D, this.shape.getBounds());
		}
	}
	
	public abstract void initDrawing(int x, int y);
	public void addPoint(int x, int y) {}	
	public abstract void keepDrawing(int x, int y);

	public void initMoving(int x, int y) {
		this.px = x;
		this.py = y;
	}
	public void keepMoving(int tx, int ty) {
		this.affineTransform.translate(tx-px, ty-py);
		px = tx;
		py = ty;
	}
}

