package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Translator extends Transformer {
	public Translator(TShape selectedShape) {
		super(selectedShape);
	}
	@Override
	public void prepare(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.prepareTranslation(x, y);
	}	
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.translate(x, y);
	}
	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
	}
	
}