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
	double cx, cy;
	int px, py;
	
	// components
	protected Shape shape;
	private TAnchors anchors;
	// working
	private AffineTransform affineTransform;
	
	private boolean bSelected;
	
	// setters and getters
	public EAnchors getSelectedAnchor() {
		return this.anchors.getESelectedAnchor();
	}
	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
		this.anchors.setESelectedAnchor(eSelectedAnchor);
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
	
	// drawing
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}

	public boolean contains(int x, int y) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if (this.bSelected) {
			if (this.anchors.contains(x, y, transformedShape.getBounds(), this.affineTransform)) {
				return true;
			}
		}		
		if(transformedShape.contains(x, y)) {
			this.anchors.setESelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		graphics2D.draw(transformedShape);
		
		if (this.bSelected) {
			this.anchors.draw(graphics2D, transformedShape.getBounds(), this.affineTransform);			
		}
	}

	public void prepareTranslation(int x, int y) {
		this.px = x;
		this.py = y;
	}
	public void translate(int x, int y) {
		this.affineTransform.translate(x-this.px, y-this.py);
		
		this.px = x;
		this.py = y;			
	}
	public void prepareResize(int x, int y) {
		Point2D p = this.anchors.getResizeAnchorPosition();
		cx = p.getX();
		cy = p.getY();		
		px = x;
		py = y;
	}
	
	public void resize(int x, int y) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		
		double width = transformedShape.getBounds().getWidth();
		double height = transformedShape.getBounds().getHeight();
		double dw = 0;
		double dh = 0;
		
		switch (this.getSelectedAnchor()) {
			case eEE: dw =  x-px; 	dh=  0; 	break;
			case eWW: dw =-(x-px);	dh=  0;		break;
			case eSS: dw =  0;		dh=  y-py;  break;
			case eNN: dw =  0;		dh=-(y-py); break;
			case eSE: dw =  x-px; 	dh=  y-py;	break;
			case eNE: dw =  x-px; 	dh=-(y-py); break;
			case eSW: dw =-(x-px);	dh=  y-py;	break;	
			case eNW: dw =-(x-px);	dh=-(y-py); break;
			default: break;
		}
		// compute resize 
		double xFactor = 1.0;
		double yFactor = 1.0;
		if (width > 0)
			xFactor = dw / width + xFactor;
		if (height > 0)			
			yFactor = dh / height + yFactor;
		
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(xFactor, yFactor);
		this.affineTransform.translate(-cx, -cy);
		
		px = x;
		py = y;
	}
}

