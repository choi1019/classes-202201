package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Drawer extends Transformer {
	public Drawer(TShape selectedShape) {
		super(selectedShape);
	}
	@Override
	public void prepare(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.prepareDrawing(x, y);
	}
	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.keepDrawing(x, y);
	}
	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
	}		
}