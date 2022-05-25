package transformers;

public class Drawer extends Transformer {
	public Drawer() {
	}
	@Override
	public void prepare(int x, int y) {
		this.shape.prepareDrawing(x, y);
	}
	@Override
	public void transform(int x, int y) {
		this.shape.keepDrawing(x, y);
	}
	@Override
	public void finalize(int x, int y) {
	}		
}
