package shape;

import java.awt.geom.Line2D;

public class GLine extends GShapeManager {
	private static final long serialVersionUID = 1L;
	// attributes
	private Line2D line;
	
	public GLine() {
		super(new Line2D.Double(0, 0 , 0, 0));
		this.line = (Line2D.Double)this.getShape();
	}
	public GLine newInstance() {
		return new GLine();
	}	
	public void setOrigin(int x, int y) {
		this.line.setLine(x, y, x, y);
	}
	public void movePoint(int newX, int newY) {
		this.line.setLine(this.line.getX1(), this.line.getY1(), newX, newY);
	}	
}
