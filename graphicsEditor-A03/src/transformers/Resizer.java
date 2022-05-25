package transformers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import shapes.TAnchors.EAnchors;

public class Resizer extends Transformer {
	public Resizer() {
	}
	
	@Override
	public void prepare(int x, int y) {
		Rectangle rectangle = this.shape.getBounds();
		Point resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y, rectangle);
		this.cx = resizeAnchorPoint.x;
		this.cy = resizeAnchorPoint.y;
		

	}	
	@Override
	public void transform(int x, int y) {
		int dx =0; int dy=0;
		EAnchors eReiszeAnchor = this.anchors.getResizeAnchor();
		switch (eReiszeAnchor) {
			case eNW: dx = (x-px); 	dy = (y-py); 	break;
			case eWW: dx = (x-px); 	dy = 0; 		break;				
			case eSW: dx = (x-px); 	dy = -(y-py);  	break;				
			case eSS: dx = 0; 		dy = -(y-py);  	break;				
			case eSE: dx = -(x-px); dy = -(y-py);  	break;				
			case eEE: dx = -(x-px); dy = 0;  		break;				
			case eNE: dx = -(x-px); dy = (y-py);  	break;				
			case eNN: dx = 0; 		dy = (y-py);  	break;				
			default: break;
		}
		Shape transformedShape = this.shape.getTransformedShape();
		double w1 = transformedShape.getBounds().width;
		double w2 = dx + w1;
		double h1 = transformedShape.getBounds().height;
		double h2 = dy + h1;
		
		double xScale = w2/w1;
		double yScale = h2/h1;
		
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(xScale, yScale);
		this.affineTransform.translate(-cx, -cy);
	}
	@Override
	public void finalize(int x, int y) {
	}		
}
