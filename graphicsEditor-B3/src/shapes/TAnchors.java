package shapes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

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
		eRR, 
		eMove
	}
	private Ellipse2D anchors[];
	private EAnchors eSelectedAnchor, eResizeAnchor;
	
	
	public EAnchors geteSelectedAnchor() {
		return eSelectedAnchor;
	}
	public void seteSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}

	
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length-1];
		for (int i=0; i<EAnchors.values().length-1; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	private void setAnchors(Rectangle BoundingRectangle) {		
		for (int i=0; i<EAnchors.values().length-1; i++) {			
			double x = BoundingRectangle.x - WIDTH/2.0;
			double y = BoundingRectangle.y - HEIGHT/2.0;
			double w = BoundingRectangle.width;
			double h = BoundingRectangle.height;
			
			EAnchors eAnchor = EAnchors.values()[i];
			switch (eAnchor) {
				case eNW: 								break;
				case eWW: 				y = y + h/2; 	break;
				case eSW:				y = y + h; 		break;
				case eSS: x = x + w/2;	y = y + h;		break;
				case eSE: x = x + w;	y = y + h;		break;
				case eEE: x = x + w;	y = y + h/2;	break;
				case eNE: x = x + w;					break;
				case eNN: x = x + w/2;					break;
				case eRR: x = x + w/2;	y = y - h/2;	break;
				default:								break;
			}
			Point2D point = new Point2D.Double(x, y);
			this.anchors[i].setFrame(point.getX(), point.getY(), WIDTH, HEIGHT);
		}
	}
	
	public boolean contains(int x, int y, Rectangle BoundingRectangle, AffineTransform affineTransform) {
		try {
			Point transformedPosition = new Point();
			affineTransform.inverseTransform(new Point(x, y), transformedPosition);
			for (int i=0; i<EAnchors.values().length-1; i++) {
				if (this.anchors[i].contains(transformedPosition.x, transformedPosition.y)) {
					this.eSelectedAnchor = EAnchors.values()[i];
					return true;
				}
			}
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2D, Rectangle BoundingRectangle, AffineTransform affineTransform) {
		this.setAnchors(BoundingRectangle);
		Point transformedPosition = new Point();
		for (int i=0; i<EAnchors.values().length-1; i++) {
			affineTransform.transform(
					new Point2D.Double(this.anchors[i].getX(), this.anchors[i].getY()), 
					transformedPosition);
			Ellipse2D transformedAnchor = new Ellipse2D.Double(
					transformedPosition.getX(), transformedPosition.getY(), 
					this.anchors[i].getWidth(), this.anchors[i].getHeight());
			graphics2D.draw(transformedAnchor);
		}
	}
	
	public Point2D getResizeAnchorPosition() {
		return new Point2D.Double(
				this.anchors[this.eResizeAnchor.ordinal()].getCenterX(),
				this.anchors[this.eResizeAnchor.ordinal()].getCenterY()
		);
	}
	
	public void setResizeAnchor() {
		switch (this.eSelectedAnchor) {
			case eNW: this.eResizeAnchor = EAnchors.eSE;	break;
			case eWW: this.eResizeAnchor = EAnchors.eEE;	break;
			case eSW: this.eResizeAnchor = EAnchors.eNE;	break;
			case eSS: this.eResizeAnchor = EAnchors.eNN;	break;
			case eSE: this.eResizeAnchor = EAnchors.eNW; 	break;
			case eEE: this.eResizeAnchor = EAnchors.eWW;	break;
			case eNE: this.eResizeAnchor = EAnchors.eSW;	break;
			case eNN: this.eResizeAnchor = EAnchors.eSS;	break;
			default: break;
		}
	}
	
	public Point2D getResizeScale(int x, int y, int px, int py, Shape shape, AffineTransform affineTransform) {
		Shape transformedShape = affineTransform.createTransformedShape(shape);
		double width = transformedShape.getBounds().width;
		double height = transformedShape.getBounds().height;
		
		double dw = x-px;
		double dh = y-py;
		switch (this.eResizeAnchor) {
			case eEE: dw = -dw;		dh=  0;		break;
			case eWW:				dh=  0;		break;
			case eSS: dw =  0;		dh=  -dh;	break;
			case eNN: dw =  0;					break;
	
			case eSE: dw =  -dw;	dh= -dh;	break;
			case eNE: dw =  -dw;				break;
			case eSW: 				dh= -dh;	break;	
			case eNW: 							break;
			default: break;
		}
		// compute resize 
		double xf = 1.0;
		double yf = 1.0;
		if (width > 0.0)
			xf = dw / width + xf;
		if (height > 0.0)			
			yf = dh / height + yf;
		
		return new Point2D.Double(xf, yf);
	}

}
