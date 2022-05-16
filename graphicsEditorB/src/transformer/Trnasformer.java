package transformer;

import java.awt.Graphics2D;
import shapes.TShape;

public abstract class Trnasformer {
	protected TShape selectedShape;
	public Trnasformer(TShape selectedShape) {
		this.selectedShape = selectedShape;
	}	
	public abstract void prepare(int x, int y, Graphics2D graphics2D);
	public abstract void keepTransforming(int x, int y, Graphics2D graphics2D);
	public abstract void finalize(int x, int y, Graphics2D graphics2D);
	
	private class Drawer extends Trnasformer {
		public Drawer(TShape selectedShape) {
			super(selectedShape);
		}
		@Override
		public void prepare(int x, int y, Graphics2D graphics2d) {
		}
		@Override
		public void keepTransforming(int x, int y, Graphics2D graphics2d) {
		}
		@Override
		public void finalize(int x, int y, Graphics2D graphics2d) {
		}		
	}
	
	private class Translator extends Trnasformer {
		public Translator(TShape selectedShape) {
			super(selectedShape);
		}
		public void prepare(int x, int y, Graphics2D graphics2D) {
			this.selectedShape.prepareMoving(x, y);
			this.selectedShape.draw(graphics2D);
		}
		public void keepTransforming(int x, int y, Graphics2D graphics2D) {
			this.selectedShape.draw(graphics2D);
			// draw
			this.selectedShape.keepMoving(x, y);
			this.selectedShape.draw(graphics2D);
		}
		@Override
		public void finalize(int x, int y, Graphics2D graphics2d) {
		}	
	}
}
