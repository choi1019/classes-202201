package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Mover extends Transformer {

	public Mover(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.prepareMoving(x, y);
	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.keepMoving(x, y);
	}

	@Override
	public void finalize(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.finalizeMoving(x, y);
	}

}
