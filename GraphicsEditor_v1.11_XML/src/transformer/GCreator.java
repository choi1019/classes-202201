package transformer;

import java.awt.Graphics2D;
import shape.GShapeManager;

public class GCreator extends GTransformer {

	public GCreator(GShapeManager selectedShape) {
		super(selectedShape);
	}
	
	public void initTransforming(Graphics2D g2D, int x, int y) {
		this.selectedShape.setOrigin(x, y);
	}
	public void transform(Graphics2D g2D, int x, int y) {
		this.selectedShape.movePoint(x, y);
	}

	public void continueTransforming(Graphics2D g2D, int x, int y) {
		this.selectedShape.addPoint(x, y);
	}

}
