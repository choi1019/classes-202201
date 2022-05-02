package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class TAnchors {
	
	private final static int WIDTH = 15;
	private final static int HEIGHT = 15;
	
	public enum EAnchors {
		eNW,
		eWW,
		eSW,
		eSS,
		eSE,
		eEE,
		eNE,
		eNN,
		eRR
	}
	private Ellipse2D anchors[];
	
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length];
		for (EAnchors eAnchor: EAnchors.values()) {
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
	}
	
	public void draw(Graphics2D graphics2D, Rectangle BoundingRectangle) {
		
		int x = BoundingRectangle.x;
		int y = BoundingRectangle.y;
		int w = BoundingRectangle.width;
		int h = BoundingRectangle.height;		
		
		for (EAnchors eAnchor: EAnchors.values()) {
			switch (eAnchor) {
			case eNW:
				break;
			case eWW:
				y = y + h/2;
				break;
			case eSW:
				y = y + h;
				break;
			case eSS:
				break;
			case eSE:
				break;
			case eEE:
				break;
			case eNE:
				break;
			case eNN:
				break;
			case eRR:
				break;
			default:
				break;
			}
			
			this.anchors[eAnchor.ordinal()].setFrame(x, y, WIDTH, HEIGHT);
			graphics2D.draw(this.anchors[eAnchor.ordinal()]);
		}
	}

}
