package transformers;

import java.awt.Graphics2D;

import shapes.TShape;

public abstract class Transformer {
	protected TShape shape;
	public Transformer(TShape shape) {
		this.shape = shape;
	}
	public abstract void prepare(int x, int y, Graphics2D graphics2D);	
	public abstract void keepTransforming(int x, int y, Graphics2D graphics2D);	
	public abstract void finalize(int x, int y, Graphics2D graphics2D);
}
