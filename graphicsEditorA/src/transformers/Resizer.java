package transformers;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shapes.TShape;
import shapes.TAnchors.EAnchors;

public class Resizer extends Transformer {
	private double xScale, yScale;
	private double cx, cy;
	
	public Resizer(TShape shape) {
		super(shape);
	}
	
	@Override
	public void prepare(int x, int y, Graphics2D graphics2d) {
		this.px = x;
		this.py = y;
		Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y);
		this.cx = resizeAnchorPoint.getX();
		this.cy = resizeAnchorPoint.getY();
	}
	
	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2d) {
		this.getResizeScale(x, y);
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(this.xScale, this.yScale);
		this.affineTransform.translate(-cx, -cy);
		this.px = x;
		this.py = y;
	}
	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
	}
	
	protected void getResizeScale(int x, int y) {
		EAnchors eReiszeAnchor = this.anchors.getResizeAnchor();
		double w1 = px - cx;
		double w2 = x - cx;
		double h1 = py - cy;
		double h2 = y - cy;
		
		switch (eReiszeAnchor) {
			case eNW: xScale = w2/w1; 	yScale = h2/h1; 	break;
			case eWW: xScale = w2/w1; 	yScale = 1; 		break;				
			case eSW: xScale = w2/w1; 	yScale = h2/h1; 	break;				
			case eSS: xScale = 1; 		yScale = h2/h1; 	break;				
			case eSE: xScale = w2/w1; 	yScale = h2/h1; 	break;				
			case eEE: xScale = w2/w1; 	yScale = 1; 		break;				
			case eNE: xScale = w2/w1; 	yScale = h2/h1; 	break;				
			case eNN: xScale = 1; 		yScale = h2/h1; 	break;				
			default: break;
		}
	}

}
