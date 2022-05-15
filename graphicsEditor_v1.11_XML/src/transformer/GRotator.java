package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shape.GShapeManager;
public class GRotator extends GTransformer {

	public GRotator(GShapeManager selectedShape) {
		super(selectedShape);
	}
	
	private double computeRotationAngle(Point2D center, Point2D previous, Point2D current) {		
		double startAngle = Math.toDegrees(
				Math.atan2(center.getX()-previous.getX(), center.getY()-previous.getY()));
		double endAngle = Math.toDegrees(
				Math.atan2(center.getX()-current.getX(), center.getY()-current.getY()));
		
		double rotationAngle = startAngle-endAngle;
		if (rotationAngle < 0) {
			rotationAngle += 360;
		}
		return rotationAngle;
	}
		
	@Override
	public void transform(Graphics2D g2D, int x, int y) {
		double rotationAngle = computeRotationAngle(
				this.selectedShape.getCenterP(), this.previous, new Point2D.Double(x, y));
		this.selectedShape.rotate(
				rotationAngle, this.selectedShape.getCenterX(), this.selectedShape.getCenterY());	
	}	
}
