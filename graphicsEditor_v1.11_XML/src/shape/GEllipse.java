package shape;

import java.awt.geom.Ellipse2D;

public class GEllipse extends GShapeManager {
	private static final long serialVersionUID = 1L;
	// attributes
	private Ellipse2D ellipse;
	
	public GEllipse() {
		super(new Ellipse2D.Double(0, 0 , 0, 0));
		this.ellipse = (Ellipse2D.Double)this.getShape();
	}
	public GEllipse newInstance() {
		return new GEllipse();
	}	
	public void setOrigin(int x, int y) {
		this.ellipse.setFrame(x, y, 
				this.ellipse.getWidth(), this.ellipse.getHeight());
	}
	public void movePoint(int newX, int newY) {
		double w = newX - this.ellipse.getX();
		double h = newY - this.ellipse.getY();
		this.ellipse.setFrame(this.ellipse.getX(), this.ellipse.getY(), w, h);
	}
}
