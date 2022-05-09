package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class TAnchors {

	private final int WIDTH = 15;
	private final int HEIGHT = 15;
	
	public enum EAnchors {
		eNW,
		eWW,
		eSW,
		eSS,
		eSE,
		eEE,
		eNE,
		eNN,
		eRR,
		eMove
	}
	private Ellipse2D anchors[];
	
	// constructors
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length-1];
		for (int i=0; i<EAnchors.values().length-1; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	// methods
	public EAnchors contains(int x, int y) {
		for (int i=0; i<EAnchors.values().length-1; i++) {			
			if (this.anchors[i].contains(x, y)) {
				return EAnchors.values()[i];
			}
		}
		return null;
	}
	
	public void draw(Graphics2D graphics2D, Rectangle boundingRectangle) {
		
		for (int i=0; i<EAnchors.values().length-1; i++) {
			EAnchors eAnchor = EAnchors.values()[i];
			int x =  boundingRectangle.x;
			int y =  boundingRectangle.y;
			int w =  boundingRectangle.width;
			int h =  boundingRectangle.height;
			
			switch (eAnchor) {
				case eNW:							break;
				case eWW:				y = y+h/2;	break;				
				case eSW:				y = y+h;	break;				
				case eSS:	x = x+w/2;	y = y+h;	break;				
				case eSE:	x = x+w;	y = y+h;	break;				
				case eEE:	x = x+w;	y = y+h/2;	break;				
				case eNE:	x = x+w;				break;				
				case eNN:	x = x+w/2;				break;				
				case eRR:	x = x+w/2;	y = y-h/2;	break;
				default:							break;
			}
			x = x - WIDTH/2;
			y = y - HEIGHT/2;
			
			this.anchors[eAnchor.ordinal()].setFrame(x, y, WIDTH, HEIGHT);
			graphics2D.draw(this.anchors[eAnchor.ordinal()]);
		}
	}
}
