package shapes;

import java.awt.Graphics2D;

abstract public class TShape {
	public abstract TShape clone();
	
	public abstract void setOrigin(int x, int y);
	public abstract void resize(int x, int y);
	public abstract void draw(Graphics2D graphics);
	
	public boolean addPoint(int x, int y) { return false; }	
	public void drawText(char c, Graphics2D graphics2dBufferedImage) {}
}

