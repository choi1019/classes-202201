package transformers;

import java.awt.geom.AffineTransform;

import shapes.TAnchors;
import shapes.TShape;

public abstract class Transformer {
	protected TShape shape;
	protected AffineTransform affineTransform;
	protected TAnchors anchors;
	protected int px, py;
	protected int cx, cy;

	public Transformer() {
	}
	public void setShape(TShape shape) {
		this.shape = shape;
		this.affineTransform = this.shape.getAffineTransform();
		this.anchors = this.shape.getAnchors();
	}
	
	public void prepareTransforming(int x, int y) {
		this.shape.setSelected(false);	
		this.prepare(x, y);
		
		this.px = x;
		this.py = y;
	}
	public void keepTransforming(int x, int y) {
		this.transform(x, y);
	
		this.px = x;
		this.py = y;
	}

	public void finalizeTransforming(int x, int y) {
		this.shape.setSelected(true);
		this.finalize(x, y);
	}
	public void continueTransforming (int x, int y) {
		this.shape.addPoint(x, y);
	}
	
	protected abstract void prepare(int x, int y);
	protected abstract void transform(int x, int y);
	protected abstract void finalize(int x, int y);
}
