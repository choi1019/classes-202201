
package shape;

import java.awt.geom.Rectangle2D;

public class GRectangle extends GShapeManager {
	private static final long serialVersionUID = 1L;
	// attributes
	private Rectangle2D rectangle;
	
	public GRectangle() {
		super(new Rectangle2D.Double(0, 0 , 0, 0));
		this.rectangle = (Rectangle2D.Double)this.getShape();
	}
	public GRectangle newInstance() {
		return new GRectangle();
	}	
	public void setOrigin(int x, int y) {
		this.rectangle.setFrame(x, y, 
				this.rectangle.getWidth(), this.rectangle.getHeight());
	}
	public void movePoint(int newX, int newY) {
		double w = newX - this.rectangle.getX();
		double h = newY - this.rectangle.getY();
		this.rectangle.setFrame(this.rectangle.getX(), this.rectangle.getY(), w, h);
	}
}
