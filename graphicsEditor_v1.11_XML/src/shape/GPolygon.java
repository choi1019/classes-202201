package shape;

import java.awt.Polygon;

public class GPolygon extends GShapeManager {
	private static final long serialVersionUID = 1L;
	// attributes
	private Polygon polygon;
	
	public GPolygon() {
		super(new Polygon());
		this.polygon = (Polygon)this.getShape();
	}
	public GPolygon newInstance() {
		return new GPolygon();
	}
	
	public void setOrigin(int x, int y) {
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
	}
	public void movePoint(int x, int y) {
		this.polygon.xpoints[polygon.npoints-1] = x;
		this.polygon.ypoints[polygon.npoints-1] = y;
	}
	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);		
	}

}
