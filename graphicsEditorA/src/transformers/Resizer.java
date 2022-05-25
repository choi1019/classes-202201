package transformers;

import java.awt.Graphics2D;

import shapes.TShape;

public class Resizer extends Transformer {
	public Resizer(TShape shape) {
		super(shape);
	}
	@Override
	public void prepare(int x, int y, Graphics2D graphics2d) {
		this.shape.prepareResizing(x, y);
	}
	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2d) {
		this.shape.keepResizing(x, y);
	}
	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
		this.shape.finalizeResizing(x, y);
	}		
}
