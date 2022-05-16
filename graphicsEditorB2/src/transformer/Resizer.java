package transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import shapes.TShape;

public class Resizer extends Transformer {
	private int px, py;
	private int cx, cy;
	
	public Resizer(TShape selectedShape) {
		super(selectedShape);
	}
	public void prepare(int x, int y, Graphics2D graphics2D) {
		Point p = this.selectedShape.getResizeAnchorPosition();
		cx = p.x;
		cy = p.y;		
		px = x;
		py = y;
	}
	public void keepTransforming(int x, int y, Graphics2D graphics2D) {
		double width = this.selectedShape.getBounds().getWidth();
		double height = this.selectedShape.getBounds().getHeight();
		double dw = 0;
		double dh = 0;
		
		switch (this.selectedShape.getSelectedAnchor()) {
			case eEE: dw =  x-px; 	dh=  0; 	break;
			case eWW: dw =-(x-px);	dh=  0;		break;
			case eSS: dw =  0;		dh=  y-py;  break;
			case eNN: dw =  0;		dh=-(y-py); break;
			case eSE: dw =  x-px; 	dh=  y-py;	break;
			case eNE: dw =  x-px; 	dh=-(y-py); break;
			case eSW: dw =-(x-px);	dh=  y-py;	break;	
			case eNW: dw =-(x-px);	dh=-(y-py); break;
			default: break;
		}
		// compute resize 
		double xFactor = 1.0;
		double yFactor = 1.0;
		if (width > 0)
			xFactor = dw / width + xFactor;
		if (height > 0)			
			yFactor = dh / height + yFactor;
		
		this.selectedShape.resize(cx, cy, xFactor, yFactor);
		
		this.px = x;
		this.py = y;
	}
	
	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
	}	
}