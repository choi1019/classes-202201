package shapes;

import java.awt.Graphics2D;

public class TLine extends TShape {
	
	private int x, y, x1, y1;
	
	public TLine() {
	}
	
	public TShape clone() {
		return new TLine();
	}
	
	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
		this.x1 = x;
		this.y1 = y;	
	}
	
	public void resize(int x, int y) {
		this.x1 = x;
		this.y1 = y;	
	}
	
	public void draw(Graphics2D graphics) {
		graphics.drawLine(x, y, x1, y1);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
