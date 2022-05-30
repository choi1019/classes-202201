package transformers;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapes.TAnchors;
import shapes.TShape;

public abstract class Transformer {
	
	protected TShape shape;
	protected AffineTransform affineTransform;	
	protected TAnchors anchors;
	
	protected int px, py;

	
	public Transformer(TShape shape) {
		this.shape = shape;
		this.affineTransform = this.shape.getAffineTransform();
		this.anchors = this.shape.getAnchors();
	}
	public abstract void prepare(int x, int y, Graphics2D graphics2D);	
	public abstract void keepTransforming(int x, int y, Graphics2D graphics2D);	
	public abstract void finalize(int x, int y, Graphics2D graphics2D);
}
