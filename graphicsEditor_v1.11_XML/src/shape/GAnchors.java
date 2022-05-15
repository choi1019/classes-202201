package shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Vector;

import global.GConstants;
import global.GConstants.EAnchors;

public class GAnchors implements Serializable {

	private static final long serialVersionUID = 1L;

	private Vector<Ellipse2D.Double> anchors;
	private EAnchors eSelectedAnchor;
	private Color lineColor, fillColor;
	
	public GAnchors() {
		this.anchors = new Vector<Ellipse2D.Double>();
		for (int i=0; i<EAnchors.values().length-1; i++) {
			this.anchors.add(new Ellipse2D.Double(0, 0, GConstants.ANCHOR_W, GConstants.ANCHOR_H));
		}
		this.eSelectedAnchor = null;
		this.lineColor = Color.BLACK;
		this.fillColor = Color.WHITE;
	}
	
	public double getCenterX(EAnchors eAnchor) {
		return this.anchors.get(eAnchor.ordinal()).getBounds().getCenterX();
	}
	public double getCenterY(EAnchors eAnchor) {
		return this.anchors.get(eAnchor.ordinal()).getBounds().getCenterY();
	}
	
	public EAnchors getSelectedAnchor() {
		return this.eSelectedAnchor;
	}
	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;		
	}
	
	public void computeCoordinate(Rectangle2D r) {
		int xOffset = GConstants.ANCHOR_W;
		int yOffset = GConstants.ANCHOR_H;
		
		double ax = r.getX() - xOffset/2;
		double ay = r.getY() - yOffset/2;
		double h = r.getHeight();
		double w = r.getWidth();
		this.anchors.get(EAnchors.E.ordinal()).setFrame(ax+w, ay+h/2, xOffset, yOffset);
		this.anchors.get(EAnchors.W.ordinal()).setFrame(ax, ay+h/2, xOffset, yOffset);
		this.anchors.get(EAnchors.S.ordinal()).setFrame(ax+w/2, ay+h, xOffset, yOffset);
		this.anchors.get(EAnchors.N.ordinal()).setFrame(ax+w/2, ay, xOffset, yOffset);
		this.anchors.get(EAnchors.NE.ordinal()).setFrame(ax+w, ay, xOffset, yOffset);
		this.anchors.get(EAnchors.NW.ordinal()).setFrame(ax, ay, xOffset, yOffset);
		this.anchors.get(EAnchors.SE.ordinal()).setFrame(ax+w, ay+h, xOffset, yOffset);
		this.anchors.get(EAnchors.SW.ordinal()).setFrame(ax, ay+h, xOffset, yOffset);
		this.anchors.get(EAnchors.R.ordinal()).setFrame(ax+w/2, ay-50, xOffset, yOffset);
	}

	public void draw(Graphics2D g2D, Shape shape) {
		Rectangle2D bounds = shape.getBounds2D();
		computeCoordinate(bounds);
		for (Ellipse2D.Double anchor: this.anchors) {
			g2D.setColor(this.fillColor);
			g2D.fill(anchor);
			g2D.setColor(this.lineColor);
			g2D.draw(anchor);
		}
	}

	public boolean contains(int x, int y) {
		for (int i=0; i<this.anchors.size(); i++) {
			if (this.anchors.get(i).contains(x, y)) {
				this.eSelectedAnchor = EAnchors.values()[i];
				return true;
			}
		}
		this.eSelectedAnchor = null;
		return false;
	}
}
