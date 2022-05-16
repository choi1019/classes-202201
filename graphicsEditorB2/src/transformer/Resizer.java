package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Resizer extends Transformer {
	public Resizer(TShape selectedShape) {
		super(selectedShape);
	}
	public void prepare(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.prepareResize(x, y);
	}
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.resize(x, y);
	}
	
	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
	}	
}