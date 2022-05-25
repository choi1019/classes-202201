package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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
	private AffineTransform affineTransform;
	// working
	private boolean bSelected;
	
	private int px, py;
	private int cx, cy;
	private int dx, dy;
	private double xScale, yScale;

	
	// constructor
	public TShape() {
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		
		this.anchors = new TAnchors();
		this.bSelected = false;
		
		dx =0;
		dy =0;
	}
	
	public abstract TShape clone();
	public void initialize() {}
	
	// setters and getters
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public EAnchors getSelectedAnchor() {
		return this.anchors.getSelectedAnchor();
	}
	
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

	public void prepareMoving(int x, int y) {
		this.setSelected(false);
		
		this.px = x;
		this.py = y;
	}	
	public void keepMoving(int x, int y) {
		this.affineTransform.translate(x - this.px, y - this.py);
		this.px = x;
		this.py = y;
	}
	public void finalizeMoving(int x, int y) {
		this.setSelected(true);
	}
	public void prepareResizing(int x, int y) {
		this.setSelected(false);
		
		this.px = x;
		this.py = y;
		Rectangle rectangle = this.shape.getBounds();
		Point resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y, rectangle);
		this.cx = resizeAnchorPoint.x;
		this.cy = resizeAnchorPoint.y;
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
		this.setSelected(true);
	}
	protected void getResizeScale(int x, int y) {
		EAnchors eReiszeAnchor = this.anchors.getResizeAnchor();
		switch (eReiszeAnchor) {
			case eNW: dx = (x-px); 	dy = (y-py); 	break;
			case eWW: dx = (x-px); 	dy = 0; 		break;				
			case eSW: dx = (x-px); 	dy = -(y-py);  	break;				
			case eSS: dx = 0; 		dy = -(y-py);  	break;				
			case eSE: dx = -(x-px); dy = -(y-py);  	break;				
			case eEE: dx = -(x-px); dy = 0;  		break;				
			case eNE: dx = -(x-px); dy = (y-py);  	break;				
			case eNN: dx = 0; 		dy = (y-py);  	break;				
			default: break;
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		double w1 = transformedShape.getBounds().width;
		double w2 = dx + w1;
		double h1 = transformedShape.getBounds().height;
		double h2 = dy + h1;
		
		this.xScale = w2/w1;
		this.yScale = h2/h1;
	}
}

