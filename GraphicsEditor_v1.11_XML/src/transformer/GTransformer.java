package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import shape.GShapeManager;

abstract public class GTransformer {
	
	protected GShapeManager selectedShape;
	protected Point2D previous;
	
	public GTransformer(GShapeManager selectedShape) {
		this.selectedShape = selectedShape;
		this.previous = new Point2D.Double();
	}
	
	public void initTransforming(Graphics2D g2D, int x, int y) {
		this.previous.setLocation(x, y);
	}
	
	abstract protected void transform(Graphics2D g2D, int x, int y);
	
	public void keepTransforming(Graphics2D g2D, int x, int y) {
		this.transform(g2D, x, y);
		this.previous.setLocation(x, y);	
	}
	
	public void continueTransforming(Graphics2D g2D, int x, int y) {
	}
	
	public void finishTransforming(Graphics2D g2D, int x, int y) {	
	}
}
