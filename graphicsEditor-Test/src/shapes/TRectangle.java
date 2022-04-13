package shapes;

import java.awt.Graphics2D;

public class TRectangle extends TShape {
	private int x, y, width, height;
	
	public TRectangle() {
	}
	
	public TShape clone() {
		return new TRectangle();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
	}
	
	public void resize(int x, int y) {
		this.width = x - this.x;
		this.height = y - this.y;		
	}
	public void draw(Graphics2D graphics) {
		graphics.drawRect(this.x, this.y, this.width, this.height);
	}
}

