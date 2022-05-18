package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Resizer extends Transformer {

	public Resizer(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y, Graphics2D graphics2D) {
	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
	}

	@Override
	public void finalize(int x, int y, Graphics2D graphics2D) {
	}

}
