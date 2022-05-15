package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import global.GConstants.EAnchors;
import shape.GShapeManager;

public class GResizer extends GTransformer {
	
	public GResizer(GShapeManager selectedShape) {
		super(selectedShape);
	}

	public Point2D computeResizeFactor(Point2D previous, Point2D current) {
		double px = previous.getX();
		double py = previous.getY();
		double cx = current.getX();
		double cy = current.getY();
		double width = this.selectedShape.getWidth();
		double height = this.selectedShape.getHeight();
		double deltaW = 0;
		double deltaH = 0;
		
		switch (this.selectedShape.getSelectedAnchor()) {
			case E:  deltaW =  cx-px; 	deltaH=  0; 	 break;
			case W:  deltaW =-(cx-px);	deltaH=  0; 	 break;
			case S:  deltaW =  0;		deltaH=  cy-py;  break;
			case N:  deltaW =  0;		deltaH=-(cy-py); break;
			case SE: deltaW =  cx-px; 	deltaH=  cy-py;	 break;
			case NE: deltaW =  cx-px; 	deltaH=-(cy-py); break;
			case SW: deltaW =-(cx-px);	deltaH=  cy-py;	 break;	
			case NW: deltaW =-(cx-px);	deltaH=-(cy-py); break;
			default: break;
		}
		// compute resize 
		double xFactor = 1.0;
		double yFactor = 1.0;
		if (width > 0.0)
			xFactor = deltaW / width + xFactor;
		if (height > 0.0)			
			yFactor = deltaH / height + yFactor;
		
		return new Point2D.Double(xFactor, yFactor);
	}
	
	@Override
	public void transform(Graphics2D g2D, int x, int y) {
		EAnchors eSelectedAnchor = this.selectedShape.getSelectedAnchor();
		Point2D resizeOrigin = this.selectedShape.getResizeOrigin(eSelectedAnchor);
		Point2D resizeFactor = this.computeResizeFactor(this.previous, new Point2D.Double(x, y));
		this.selectedShape.resize(resizeFactor, resizeOrigin, eSelectedAnchor);
	}
}
