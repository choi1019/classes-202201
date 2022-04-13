package shapes;

import java.awt.Graphics2D;

public class TPolygon extends TShape {
	private final int MAX_POINTS = 20;

	private int[] xPoints;
	private int[] yPoints;
	private int nPoints;
	
	public TPolygon() {
		this.xPoints = new int[MAX_POINTS];
		this.yPoints = new int[MAX_POINTS];		
	}	
	public TShape clone() {
		return new TPolygon();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.nPoints = 0;		
		this.addPoint(x, y);
		this.addPoint(x, y);
	}
	
	public boolean addPoint(int x, int y) {
		this.xPoints[this.nPoints] = x;
		this.yPoints[this.nPoints] = y;
		this.nPoints++;	
		System.out.println(this.nPoints);
		if (this.nPoints == MAX_POINTS)
			return false;
		return true;
	}
	
	@Override
	public void resize(int x, int y) {
		this.xPoints[this.nPoints-1] = x;
		this.yPoints[this.nPoints-1] = y;
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawPolyline(xPoints, yPoints, nPoints);

	}

}
