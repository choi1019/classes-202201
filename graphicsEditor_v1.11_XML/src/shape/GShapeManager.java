package shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

import global.GConstants.EAnchors;

public abstract class GShapeManager implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;	
	
	// attributes
	private Color lineColor, fillColor;
	
	// components
	protected Shape shape;
	private GAnchors anchors;
	
	// working variables
	private boolean selected;
	private EAnchors selectedAnchor;
	
	protected AffineTransform affineTransform;
	
	// setters & getters	
	public Color getLineColor() { return this.lineColor; }
	public void setLineColor(Color lineColor) { this.lineColor = lineColor; }
	public Color getFillColor() { return this.fillColor; }
	public void setFillColor(Color fillColor) { this.fillColor = fillColor; }
	
	public Shape getShape() { return this.shape; }	
	public void setShape(Shape shape) {	this.shape = shape;	}
	
	public GAnchors getAnchors() { return this.anchors;	}

	public boolean isSelected() { return selected; }
	public void setSelected(boolean selected) {	this.selected = selected; }
	
	public EAnchors getSelectedAnchor() { return this.selectedAnchor; }	
	public void setSelectedAnchor(EAnchors selectedAnchor) { this.selectedAnchor = selectedAnchor; }	

	// constructors
	public GShapeManager(Shape shape) {
		this.lineColor = Color.BLACK;
		this.fillColor = null;
		
		this.shape = shape;
		this.anchors = new GAnchors();		
		
		this.selected = false;
		this.selectedAnchor = null;

		this.affineTransform = new AffineTransform();
	}
	
	public GShapeManager clone() {
		try {
			return (GShapeManager) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}	
	abstract public GShapeManager newInstance();
	
	// methods
	public void draw(Graphics2D g2D) {
		if (this.fillColor != null) {
			g2D.setColor(this.fillColor);
			g2D.fill(this.shape);
		}
		g2D.setColor(this.lineColor);
		g2D.draw(this.shape);
	}
	public void drawAnchors(Graphics2D g2D) {
		this.anchors.draw(g2D, this.shape);
	}
	
	public boolean contains(int x, int y) {
		if (this.selected) {
			if (this.anchors.contains(x, y)) {
				this.selectedAnchor = this.anchors.getSelectedAnchor();
				return true;
			}
		}
		if (this.shape.getBounds2D().contains(x, y)) {
			this.selectedAnchor = EAnchors.M;
			return true;
		}
		return false;			
	}	
	public boolean contains(GShapeManager shapeManager) {
		return this.shape.contains(shapeManager.getShape().getBounds2D());
	}
	
	// create
	public abstract void setOrigin(int x, int y);
	public abstract void movePoint(int x, int y);
	public void addPoint(int x, int y) {}
	
	// move
	public void move(double offsetX, double offsetY) {
		this.affineTransform.setToTranslation(offsetX, offsetY);
		this.shape = this.affineTransform.createTransformedShape(this.shape);
	}
	public void moveALittle() {
		this.affineTransform.setToTranslation(8, 8);
		this.shape = this.affineTransform.createTransformedShape(this.shape);
	}

	// rotate		
	public Point2D getCenterP() { 
		return new Point2D.Double(
			this.shape.getBounds2D().getCenterX(), this.shape.getBounds2D().getCenterY()); 
	}
	public double getCenterX() { return this.shape.getBounds2D().getCenterX(); }
	public double getCenterY() { return this.shape.getBounds2D().getCenterY(); }	
	public void rotate(double rotationAngle, double centerX, double centerY) {
		this.affineTransform.setToRotation(Math.toRadians(rotationAngle), centerX, centerY);
		this.shape = this.affineTransform.createTransformedShape(this.shape);			
	}
	
	// resize
	public double getWidth() { return this.shape.getBounds2D().getWidth(); }
	public double getHeight() { return this.shape.getBounds2D().getHeight(); }	
	public Point2D getResizeOrigin(EAnchors eSelectedAnchor) {
		Point2D p = new Point2D.Double();
		GAnchors anchors = this.getAnchors();
		switch (eSelectedAnchor) { 
			case E: p.setLocation(anchors.getCenterX(EAnchors.W), 	0); 	 break;
			case W: p.setLocation(anchors.getCenterX(EAnchors.E), 	0); 	 break;
			case S: p.setLocation(0, 			  					anchors.getCenterY(EAnchors.N)); break;
			case N: p.setLocation(0, 			  					anchors.getCenterY(EAnchors.S)); break;
			case SE: p.setLocation(anchors.getCenterX(EAnchors.NW), anchors.getCenterY(EAnchors.NW)); break;
			case NE: p.setLocation(anchors.getCenterX(EAnchors.SW), anchors.getCenterY(EAnchors.SW)); break;
			case SW: p.setLocation(anchors.getCenterX(EAnchors.NE), anchors.getCenterY(EAnchors.NE)); break;
			case NW: p.setLocation(anchors.getCenterX(EAnchors.SE), anchors.getCenterY(EAnchors.SE)); break;
			default: break;
		}
		return p;
	}
	public void resize(Point2D resizeFactor, Point2D resizeOrigin, EAnchors eSelectedAnchor) {
		this.affineTransform.setToTranslation(resizeOrigin.getX(), resizeOrigin.getY());
		this.affineTransform.scale(resizeFactor.getX(), resizeFactor.getY());
		this.affineTransform.translate(-resizeOrigin.getX(), -resizeOrigin.getY());
		this.shape = this.affineTransform.createTransformedShape(this.shape);	
	}	
}
