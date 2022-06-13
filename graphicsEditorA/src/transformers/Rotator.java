package transformers;

import shapes.TShape;

public class Rotator extends Transformer {
	private double cx, cy;
	public Rotator(TShape shape) {
		super(shape);
	}
	@Override
	public void prepare(int x, int y) {
		this.cx = this.shape.getCenterX();
		this.cy = this.shape.getCenterY();
		this.px = x;
		this.py = y;
	}
	@Override
	public void keepTransforming(int x, int y) {
		double startAngle = Math.atan2(px-cx, py-cy);
		double endAngle = Math.atan2(x-cx, y-cy);
		double angle = endAngle - startAngle;
		
		this.affineTransform.rotate(angle);
		
		this.px = x;
		this.py = y;
	}
	@Override
	public void finalize(int x, int y) {
	}		
}
