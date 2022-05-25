package transformers;

public class Mover extends Transformer {
	public Mover() {
	}
	@Override
	public void prepare(int x, int y) {
		this.px = x;
		this.py = y;
	}
	@Override
	public void transform(int x, int y) {
		this.affineTransform.translate(x - px, y - py);
		
		this.px = x;
		this.py = y;
	}
	@Override
	public void finalize(int x, int y) {
	}		
}
