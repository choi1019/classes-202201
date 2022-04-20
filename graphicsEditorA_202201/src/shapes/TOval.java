package shapes;

import java.awt.Graphics2D;

public class TOval extends TShape {
	
	private int x, y, w, h;
	
	public TOval() {
	}
	
	public TShape clone() {
		return new TOval();
	}
	
	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
		this.w = 0;
		this.h = 0;	
	}
	
	public void resize(int x, int y) {
		this.w = x-this.x;
		this.h = y-this.y;
	}
	
	public void draw(Graphics2D graphics) {
		graphics.drawOval(x, y, w, h);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
