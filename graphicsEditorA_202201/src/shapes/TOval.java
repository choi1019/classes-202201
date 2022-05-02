package shapes;

import java.awt.geom.Ellipse2D;

public class TOval extends TShape {	
	private static final long serialVersionUID = 1L;

	public TOval() {
		this.shape = new Ellipse2D.Double();
	}
	
	public TShape clone() {
		return new TOval();
	}
	
	public void setOrigin(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
	}
	
	public void resize(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(ellipse.getX(), ellipse.getY(), x-ellipse.getX(), y-ellipse.getY());
	}
}
