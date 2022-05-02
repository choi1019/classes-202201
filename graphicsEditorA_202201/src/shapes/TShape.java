package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;

abstract public class TShape implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Shape shape;
	private TAnchors anchors;
	
	public void drawAnchor() {}
	public abstract TShape clone();
	
	public abstract void setOrigin(int x, int y);
	public abstract void resize(int x, int y);
	
	public void draw(Graphics2D graphics) {
		graphics.draw(this.shape);
	}
	public boolean contains(int x, int y) {
		return this.shape.contains(x, y);
	}	
	public void addPoint(int x, int y) {}
}

