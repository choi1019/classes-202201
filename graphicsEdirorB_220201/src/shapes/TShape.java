package shapes;


import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;

abstract public class TShape implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Shape shape;
	private TAnchors anchors;
	
	public TShape() {
		this.anchors = new TAnchors();
	}
	
	public abstract void setOrigin(int x, int y);
	public abstract void resize(int x, int y);

	public void draw(Graphics2D graphics2D) {
		graphics2D.draw(this.shape);
	}
	public void drawAnchors(Graphics2D graphics2D) {
		this.anchors.draw(graphics2D, this.shape.getBounds());
	}
	
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return this.shape.contains(x, y);
	}
	
	public abstract TShape clone();	
	public void addPoint(int x, int y) {}

}

