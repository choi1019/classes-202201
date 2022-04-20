package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class TRectangle extends TShape {
	
	public TRectangle() {
		this.shape = new Rectangle();
	}
	
	public TShape clone() {
		return new TRectangle();
	}
	
	public void setOrigin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setBounds(x, y, 0, 0);
	}
	
	@Override
	public void resize(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x-rectangle.x, y-rectangle.y);
	}
	@Override
	public void draw(Graphics2D graphics) {
		graphics.draw(this.shape);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return this.shape.contains(x, y);
	}
}
