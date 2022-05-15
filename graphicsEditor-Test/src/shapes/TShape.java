package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	private int px, py;
	private int cx, cy;
	
	// attributes
	private static final long serialVersionUID = 1L;
	// components
	protected Shape shape;
	private TAnchors anchors;
	// working
	protected AffineTransform affineTransform;
	
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
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
	}
	public abstract TShape clone();	
	
	// methods
	public abstract void initDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);	
	public void addPoint(int x, int y) {}

	public boolean contains(int x, int y) {
		Point point = new Point(x, y);
		try {
			this.affineTransform.inverseTransform(point, point);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = point.x;
		y = point.y;
		
		if (this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x, y);
			if (this.eSelectedAnchor != null) {
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
		if (this.bSelected) {
			this.anchors.draw(graphics2D, this.shape.getBounds());			
		}		
	}
	
	public void drawAnchors(Graphics2D graphics2D) {
		this.anchors.draw(graphics2D, this.shape.getBounds());			
	}
	
	public void initTranslation(int x, int y) {
		this.px = x;
		this.py = y;
	}
	public void translate(int tx, int ty) {
		this.affineTransform.translate(tx-px, ty-py);
		px = tx;
		py = ty;
	}
	public void initRotation(int x, int y) {
		this.px = x;
		this.py = y;
		this.cx = (int) this.shape.getBounds().getCenterX();
		this.cy = (int) this.shape.getBounds().getCenterY();
		
	}
	public void rotate(int x, int y) {
		double startAngle = Math.toDegrees(Math.atan2(cx-px, cy-py));
		double endAngle = Math.toDegrees(Math.atan2(cx-x, cy-y));
		
		double rotationAngle = startAngle-endAngle;
		if (rotationAngle < 0) {
			rotationAngle += 360;
		}
		this.affineTransform.rotate(Math.toRadians(rotationAngle), cx, cy);
		this.px = x;
		this.py = y;
	}
	
	public void initResize(int x, int y) {
		Point p = new Point();
		switch (this.eSelectedAnchor) {
			case eEE: p.setLocation(anchors.getCenterX(EAnchors.eWW), 	0); 	 break;
			case eWW: p.setLocation(anchors.getCenterX(EAnchors.eEE), 	0); 	 break;
			case eSS: p.setLocation(0, 			  						anchors.getCenterY(EAnchors.eNN)); break;
			case eNN: p.setLocation(0, 			  						anchors.getCenterY(EAnchors.eSS)); break;
			case eSE: p.setLocation(anchors.getCenterX(EAnchors.eNW), 	anchors.getCenterY(EAnchors.eNW)); break;
			case eNE: p.setLocation(anchors.getCenterX(EAnchors.eSW), 	anchors.getCenterY(EAnchors.eSW)); break;
			case eSW: p.setLocation(anchors.getCenterX(EAnchors.eNE), 	anchors.getCenterY(EAnchors.eNE)); break;
			case eNW: p.setLocation(anchors.getCenterX(EAnchors.eSE), 	anchors.getCenterY(EAnchors.eSE)); break;
			default: break;
		}
		px = x;
		py = y;
		cx = p.x;
		cy = p.y;
	}
	
	public void resize(int x, int y) {
		double width = this.shape.getBounds().getWidth();
		double height = this.shape.getBounds().getHeight();
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
		
		this.px = x;
		this.py = y;
	}
	public void finishResize(int x, int y) {
	}
}

