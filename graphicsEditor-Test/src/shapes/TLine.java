package shapes;

import java.awt.Graphics2D;

public class TLine extends TShape {
	private int x0, y0, x1, y1;
	
	public TLine() {
	}
	
	public TShape clone() {
		return new TLine();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.x0 = x;
		this.y0 = y;
		this.x1 = x;
		this.y1 = y;
	}
	public void resize(int x, int y) {
		this.x1 = x;
		this.y1 = y;		
	}
	public void draw(Graphics2D graphics) {
		graphics.drawLine(this.x0, this.y0, this.x1, this.y1);
	}
}