package transformer;

import shapes.TShape;

public class Rotator extends Transformer {

	private double cx, cy;
	public Rotator(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y) {
		this.cx = this.selectedShape.getCenterX();
		this.cy = this.selectedShape.getCenterY();
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(int x, int y) {
		double startAngle = Math.atan2(py-cy, px-cx);
		double endAngle = Math.atan2(y-cy, x-cx);
		double angle = endAngle - startAngle;
		
		this.affineTransform.rotate(angle);
		
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalize(int x, int y) {
	}

}
