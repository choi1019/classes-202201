package shapes;


import java.awt.Graphics2D;

public class TRectangle extends TShape {
	
	private int x, y, w, h;
	
	public TRectangle() {
	}
	
	public TShape clone() {
		return new TRectangle();
	}
	
	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
		this.w = 0;
		this.h = 0;		
	}
	
	@Override
	public void resize(int x, int y) {
		this.w = x-this.x;
		this.h = y-this.y;
	}
	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawRect(x, y, w, h);
	}
}
