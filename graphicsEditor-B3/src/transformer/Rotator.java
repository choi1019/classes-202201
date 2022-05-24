package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Rotator extends Transformer {

	public Rotator(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.initRotation(x, y);
	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.rotate(x, y);
	}

	@Override
	public void finalize(int x, int y, Graphics2D graphics2D) {
	}

}
