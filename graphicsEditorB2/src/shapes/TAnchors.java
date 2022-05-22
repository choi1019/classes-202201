package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class TAnchors {
	
	private final static int WIDTH = 10;
	private final static int HEIGHT = 10;
	
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
	
	public EAnchors getESelectedAnchor() {
		return this.eSelectedAnchor;
	}
	public void setESelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}
	
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length-1];
		for (int i=0; i<EAnchors.values().length-1; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	public boolean contains(int x, int y, Rectangle BoundingRectangle, AffineTransform affineTransform) {
		for (int i=0; i<EAnchors.values().length-1; i++) {			
//			Point2D origin = new Point2D.Double(this.anchors[i].getX(), this.anchors[i].getY());
//			affineTransform.transform(origin, origin);
//			Ellipse2D transformedAnchor = new Ellipse2D.Double(origin.getX(), origin.getY(), this.anchors[i].getWidth(), this.anchors[i].getHeight());
			if (this.anchors[i].contains(x, y)) {
				this.eSelectedAnchor = EAnchors.values()[i];				
				return true;
			}
		}
		return false;
	}
	
	public Point2D getResizeAnchorPosition() {
		Point2D p = new Point2D.Double();

		switch (this.eSelectedAnchor) {
			case eEE: p.setLocation(anchors[EAnchors.eWW.ordinal()].getCenterX(), 0); 	 break;
			case eWW: p.setLocation(anchors[EAnchors.eEE.ordinal()].getCenterX(), 0); 	 break;
			case eSS: p.setLocation(0, 			  								  anchors[EAnchors.eNN.ordinal()].getCenterY()); break;
			case eNN: p.setLocation(0, 			  								  anchors[EAnchors.eSS.ordinal()].getCenterY()); break;
			case eSE: p.setLocation(anchors[EAnchors.eNW.ordinal()].getCenterX(), anchors[EAnchors.eNW.ordinal()].getCenterY()); break;
			case eNE: p.setLocation(anchors[EAnchors.eSW.ordinal()].getCenterX(), anchors[EAnchors.eSW.ordinal()].getCenterY()); break;
			case eSW: p.setLocation(anchors[EAnchors.eNE.ordinal()].getCenterX(), anchors[EAnchors.eNE.ordinal()].getCenterY()); break;
			case eNW: p.setLocation(anchors[EAnchors.eSE.ordinal()].getCenterX(), anchors[EAnchors.eSE.ordinal()].getCenterY()); break;
		default: break;
		}
		return p;
	}

	private void setAnchors(Rectangle BoundingRectangle, AffineTransform affineTransform) {
		for (int i=0; i<EAnchors.values().length-1; i++) {
			double x = BoundingRectangle.x - WIDTH/2;
			double y = BoundingRectangle.y - HEIGHT/2;
			double w = BoundingRectangle.width;
			double h = BoundingRectangle.height;
			
			EAnchors eAnchor = EAnchors.values()[i];
			switch (eAnchor) {
			case eNW: 								break;
			case eWW: 				y = y + h/2; 	break;
			case eSW: 				y = y + h;		break;
			case eSS: x = x + w/2;	y = y + h;		break;
			case eSE: x = x + w;	y = y + h;		break;
			case eEE: x = x + w;	y = y + h/2;	break;
			case eNE: x = x + w;					break;
			case eNN: x = x + w/2;					break;
			case eRR: x = x + w/2;	y = y - h/2; 	break;
			default: 								break;
			}
			
			this.anchors[i].setFrame(x, y, WIDTH, HEIGHT);
			
//			Point2D origin = new Point2D.Double(this.anchors[i].getX(), this.anchors[i].getY());
//			affineTransform.transform(origin, origin);
//			Ellipse2D transformedAnchor = new Ellipse2D.Double(origin.getX(), origin.getY(), this.anchors[i].getWidth(), this.anchors[i].getHeight());
		}
	
	}
	public void draw(Graphics2D graphics2D, Rectangle BoundingRectangle, AffineTransform affineTransform) {
		this.setAnchors(BoundingRectangle, affineTransform);
		for (int i=0; i<EAnchors.values().length-1; i++) {
			Color forground = graphics2D.getColor();
			graphics2D.setColor(graphics2D.getBackground());
			graphics2D.fill(this.anchors[i]);
			graphics2D.setColor(forground);
			graphics2D.draw(this.anchors[i]);
		}
	}
}
