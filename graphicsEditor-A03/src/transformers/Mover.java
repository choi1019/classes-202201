package transformers;

public class Mover extends Transformer {
	public Mover() {
	}
	@Override
	public void prepare(int x, int y) {
	}
	@Override
	public void transform(int x, int y) {
		this.affineTransform.translate(x - px, y - py);
	}
	@Override
	public void finalize(int x, int y) {
	}		
}
