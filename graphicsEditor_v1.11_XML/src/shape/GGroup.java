package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import global.GConstants.EAnchors;

public class GGroup extends GShapeManager {
	private static final long serialVersionUID = 1L;
	
	// components
	private Rectangle2D rectangle;
	private Vector<GShapeManager> shapeVector;	
	
	// setters & getters
	public Vector<GShapeManager> getShapes() { return this.shapeVector; }
	
	public void setLineColor(Color lineColor) { 
		for (GShapeManager shape: this.shapeVector) {
			shape.setLineColor(lineColor);
		}
	}
	public void setFillColor(Color fillColor) { 
		for (GShapeManager shape: this.shapeVector) {
			shape.setFillColor(fillColor);
		}
	}	
	
	// constructors
	public GGroup() {
		super(new Rectangle2D.Double());
		
		this.rectangle = (Rectangle2D.Double)this.getShape();
		this.shapeVector = new Vector<GShapeManager>();
	}	
	public GShapeManager clone() {
		GGroup group = new GGroup();
		for (GShapeManager shape : this.getShapes()) {
			GShapeManager shapeClone = shape.clone();
			group.add(shapeClone);
		}
		return group;
	}	
	public GGroup newInstance() {
		return new GGroup();
	}
	public void add(GShapeManager shape) {
		this.getShapes().add(shape);
		if (this.getShapes().size() == 1) {
			this.rectangle = shape.getShape().getBounds2D();
		} else {
			Rectangle2D.union(this.rectangle, shape.getShape().getBounds2D(), this.rectangle);
		}
		this.setShape(this.rectangle);
	}
	
	// methods
	public void draw(Graphics2D g2D) {
		for (GShapeManager shape: this.getShapes()) {
			shape.draw(g2D);
		}
		Stroke savedStroke = g2D.getStroke();
		Stroke stroke = new BasicStroke(
				1.0f,						// Width
	            BasicStroke.CAP_SQUARE,		// End cap
	            BasicStroke.JOIN_MITER,		// Join style
	            10.0f,						// Miter limit
	            new float[] {2.0f,5.0f},	// Dash pattern
	            0.0f						// Dash phase
	    );                     
		g2D.setStroke(stroke);
		g2D.draw(this.getShape());
		g2D.setStroke(savedStroke);
	}
	
	public void move(double offsetX, double offsetY) {
		super.move(offsetX, offsetY);
		for (GShapeManager shape: this.getShapes()) {
			shape.move(offsetX, offsetY);
		}
	}
	public void moveALittle() {
		super.moveALittle();
		for (GShapeManager shape: this.getShapes()) {
			shape.moveALittle();
		}
	}

	public void rotate(double rotationAngle, double centerX, double centerY) {
		super.rotate(rotationAngle, centerX, centerY);
		for (GShapeManager shape: this.getShapes()) {
			shape.rotate(rotationAngle, centerX, centerY);
		}
	}
	
	// resize
	public void resize(Point2D resizeFactor,Point2D resizeOrigin, EAnchors eSelectedAnchor) {
		super.resize(resizeFactor, resizeOrigin, eSelectedAnchor);
		for (GShapeManager shape: this.getShapes()) {
			shape.resize(resizeFactor, resizeOrigin, eSelectedAnchor);
		}
	}

	@Override
	public void setOrigin(int x, int y) {}
	@Override
	public void movePoint(int x, int y) {}	
}
