package shape;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import global.GConstants.EAnchors;

public class GSelectContainer extends GGroup {
	private static final long serialVersionUID = 1L;
	
	// working objects
	private GShapeManager selectedShape;

	public void setSelected(boolean selected) {
		for (GShapeManager shape: this.getShapes()) {
			shape.setSelected(selected);
		}
	}	
	public GAnchors getAnchors() { return this.selectedShape.getAnchors(); }
	
	// constructors
	public GSelectContainer() {
		this.selectedShape = null;
	}	
	public GShapeManager clone() {
		GSelectContainer preGroup = new GSelectContainer();
		for (GShapeManager shape : this.getShapes()) {
			GShapeManager shapeClone = shape.clone();
			preGroup.add(shapeClone);
		}
		return preGroup;
	}	
	public GSelectContainer newInstance() {
		return new GSelectContainer();
	}
	
	// methods
	public void draw(Graphics2D g2D) {
		for (GShapeManager shape: this.getShapes()) {
			shape.draw(g2D);
		}
	}
	public void drawAnchors(Graphics2D g2D) {
		for (GShapeManager shape: this.getShapes()) {
			shape.drawAnchors(g2D);
		}
	}
	
	public boolean contains(int x, int y) {
		for (GShapeManager shape: this.getShapes()) {
			if (shape.contains(x, y)) {
				this.setSelectedAnchor(shape.getSelectedAnchor());
				this.selectedShape = shape;
				return true;
			}
		}
		return false;
	}

	// move
	public void move(double offsetX, double offsetY) {
		for (GShapeManager shape: this.getShapes()) {
			shape.move(offsetX, offsetY);
		}
	}
	public void moveALittle() {
		for (GShapeManager shape: this.getShapes()) {
			shape.moveALittle();
		}
	}

	// rotate	
	public void rotate(double rotationAngle, double centerX, double centerY) {
		for (GShapeManager shape: this.getShapes()) {
			shape.rotate(rotationAngle, shape.getCenterX(), shape.getCenterY());
		}
	}
	
	// resize
	public void resize(Point2D resizeFactor, Point2D resizeOrigin, EAnchors eSelectedAnchor) {
		for (GShapeManager shape: this.getShapes()) {
			shape.resize(resizeFactor, shape.getResizeOrigin(eSelectedAnchor), eSelectedAnchor);
		}
	}	
}
