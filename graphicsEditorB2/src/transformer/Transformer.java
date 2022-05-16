package transformer;

import java.awt.Graphics2D;
import shapes.TShape;

public abstract class Transformer {
	protected TShape selectedShape;
	
	public Transformer(TShape selectedShape) {
		this.selectedShape = selectedShape;
	}
	
	public abstract void prepare(int x, int y, Graphics2D graphics2D);
	public abstract void keepTransforming(int x, int y, Graphics2D graphics2D);
	public abstract void finalize(int x, int y, Graphics2D graphics2D);
	
	public void addPoint(int x, int y) {this.selectedShape.addPoint(x, y);}
}
