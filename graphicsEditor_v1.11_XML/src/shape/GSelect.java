package shape;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

public class GSelect extends GShapeManager {
	private static final long serialVersionUID = 1L;
	
	// components
	private Rectangle2D rectangle;
	
	// constructors
	public GSelect() {
		super(new Rectangle2D.Double(0, 0 , 0, 0));
		this.rectangle = (Rectangle2D.Double)this.getShape();
	}	
	public GSelect newInstance() {
		return new GSelect();
	}
	
	// methods
	public void draw(Graphics2D g2D) {
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
		g2D.draw(this.rectangle);
		g2D.setStroke(savedStroke);
	}
	
	// create
	public boolean contains(GShapeManager gShapeManager) {
		if (this.rectangle.contains(gShapeManager.getShape().getBounds())) {
			return true;
		}
		return false;
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
