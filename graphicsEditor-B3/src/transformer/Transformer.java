package transformer;

import java.awt.Graphics2D;
import shapes.TShape;

public class Transformer {
	
	protected TShape selectedShape;
	
	public Transformer(TShape selectedShape) {
		this.selectedShape = selectedShape;
	}
	
	public void prepare(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.setSelected(false);
	}
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		
	}
	public void finalize(int x, int y, Graphics2D graphics2D) {
		this.selectedShape.setSelected(true);
	}

}
