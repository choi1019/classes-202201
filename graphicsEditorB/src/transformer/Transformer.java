package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapes.TAnchors;
import shapes.TShape;

public abstract class Transformer {
	
	protected TShape selectedShape;
	protected AffineTransform affineTransform;
	protected TAnchors anchors;
	// working
	protected int px, py;
	protected double cx, cy;
	protected double xScale, yScale;
	
	public Transformer(TShape selectedShape) {
		this.selectedShape = selectedShape;
		this.affineTransform = this.selectedShape.getAffineTransform();
		this.anchors = this.selectedShape.getAnchors();
	}
	
	public abstract void prepare(int x, int y, Graphics2D graphics2D);
	public abstract void keepTransforming(int x, int y, Graphics2D graphics2D);
	public abstract void finalize(int x, int y, Graphics2D graphics2D);

}
