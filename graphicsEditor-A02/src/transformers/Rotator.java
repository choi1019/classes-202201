package transformers;

public class Rotator extends Transformer {
	public Rotator() {
	}
	@Override
	public void prepare(int x, int y) {
		this.cx = (int) this.shape.getBounds().getCenterX();
		this.cy = (int) this.shape.getBounds().getCenterY();
	}
	@Override
	public void transform(int x, int y) {
		double startAngle = Math.toDegrees(Math.atan2(cx-px, cy-py));
		double endAngle = Math.toDegrees(Math.atan2(cx-x, cy-y));
		
		double rotationAngle = startAngle-endAngle;
		if (rotationAngle < 0) {
			rotationAngle += 360;
		}
		this.affineTransform.rotate(Math.toRadians(rotationAngle), cx, cy);
	}
	@Override
	public void finalize(int x, int y) {
	}		
}
