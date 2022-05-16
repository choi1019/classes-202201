package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Translator extends Transformer {
	public Translator(TShape selectedShape) {
		super(selectedShape);
	}
	public void prepare(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.prepareMoving(x, y);
		this.selectedShape.draw(graphics2D);
	}
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.keepMoving(x, y);
	}
	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
	}	
}