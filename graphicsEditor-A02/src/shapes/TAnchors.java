package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
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
	private EAnchors eSelectedAnchor;
	private EAnchors eReiszeAnchor;
	
	public EAnchors getSelectedAnchor() {
		return this.eSelectedAnchor;
	}
	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}
	public EAnchors getResizeAnchor() {
		return this.eReiszeAnchor;
	}
	
	// constructors
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length-1];
		for (int i=0; i<EAnchors.values().length-1; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	// methods
	public boolean contains(int x, int y) {
		for (int i=0; i<EAnchors.values().length-1; i++) {			
			if (this.anchors[i].contains(x, y)) {
				this.eSelectedAnchor = EAnchors.values()[i];
				return true;
			}
		}
		return false;
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
				case eRR:	x = x+w/2;	y = y-50;	break;
				default:							break;
			}
			x = x - WIDTH/2;
			y = y - HEIGHT/2;
			
			this.anchors[eAnchor.ordinal()].setFrame(x, y, WIDTH, HEIGHT);
			graphics2D.draw(this.anchors[eAnchor.ordinal()]);
		}
	}
	
	public Point getResizeAnchorPoint(int x, int y, Rectangle r) {
		int cx=0, cy=0;
		this.eReiszeAnchor = null;
		switch (this.eSelectedAnchor) {
			case eNW: eReiszeAnchor = EAnchors.eSE; cx=r.x+r.width; 	cy=r.y+r.height; 	break;
			case eWW: eReiszeAnchor = EAnchors.eEE; cx=r.x+r.width;		cy=r.y+r.height/2; 	break;				
			case eSW: eReiszeAnchor = EAnchors.eNE; cx=r.x+r.width;		cy=r.y; 			break;				
			case eSS: eReiszeAnchor = EAnchors.eNN; cx=r.x+r.width/2;	cy=r.y; 			break;				
			case eSE: eReiszeAnchor = EAnchors.eNW; cx=r.x; 			cy=r.y;			 	break;				
			case eEE: eReiszeAnchor = EAnchors.eWW; cx=r.x; 			cy=r.y+r.height/2; 	break;				
			case eNE: eReiszeAnchor = EAnchors.eSW; cx=r.x; 			cy=r.y+r.height; 	break;				
			case eNN: eReiszeAnchor = EAnchors.eSS; cx=r.x+r.width/2;	cy=r.y+r.height; 			break;				
			default: break;
		}
		return new Point(cx, cy);
	}
}
