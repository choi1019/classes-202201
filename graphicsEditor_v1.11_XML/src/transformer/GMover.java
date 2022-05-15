package transformer;

import java.awt.Graphics2D;
import shape.GShapeManager;

public class GMover extends GTransformer {
	
	public GMover(GShapeManager selectedShape) {
		super(selectedShape);
	}
	
	@Override
	public void transform(Graphics2D g2D, int x, int y) {
		this.selectedShape.move(x-this.previous.getX(), y-this.previous.getY());
	}
}