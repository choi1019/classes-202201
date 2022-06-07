package shapes;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import global.Constants.ETransformers;
import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable {
	// attributes
	private static final long serialVersionUID = 1L;
	// components
	protected Shape shape;
	protected TAnchors anchors;
	protected AffineTransform affineTransform;
	// working
	private boolean bSelected;
	
	// constructor
	public TShape() {
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();	
		this.anchors = new TAnchors();
		this.bSelected = false;
	}
	
	public abstract TShape clone();
	public void initialize() {}
	
	// setters and getters
	public AffineTransform getAffineTransform() {
		return this.affineTransform;
	}
	public TAnchors getAnchors() {
		return this.anchors;
	}
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}	
	public ETransformers getTransformer() {
		EAnchors eAnchor = this.anchors.getSelectedAnchor();
		if(eAnchor == EAnchors.eMove) {
			return ETransformers.eMover;
		} else if(eAnchor == EAnchors.eRR) {
			return ETransformers.eRotator;
		} else {							
			return ETransformers.eResizer;
		}		
	}
	
	public Shape getTransformedShape() {
		return this.affineTransform.createTransformedShape(this.shape);
	}
	public Rectangle getBounds() {
		return this.shape.getBounds();
	}
	
	// methods
	public boolean contains(int x, int y) {		
		if (isSelected()) {
			if (this.anchors.contains(x, y)) {
				return true;
			}
		}
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if(transformedShape.contains(x, y)) {
			this.anchors.setSelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		graphics2D.draw(transformedShape);		
		if (isSelected()) {
			this.anchors.draw(graphics2D, transformedShape.getBounds(), this.affineTransform);
		}
	}

	public void drawAnchors(Graphics2D graphics2D) {
		this.anchors.draw(graphics2D, this.shape.getBounds(), this.affineTransform);
	}	
	
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}

	public Cursor getCursor() {		
		Cursor cursor = new Cursor(Cursor.MOVE_CURSOR);
		if (this.isSelected()) {
			EAnchors eAnchor = this.anchors.getSelectedAnchor();
			switch(eAnchor) {
				case eRR: cursor = new Cursor(Cursor.HAND_CURSOR); break;
				case eNW: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
				case eWW: cursor = new Cursor(Cursor.W_RESIZE_CURSOR); break;
				case eSW: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
				case eSS: cursor = new Cursor(Cursor.S_RESIZE_CURSOR); break;
				case eSE: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
				case eEE: cursor = new Cursor(Cursor.E_RESIZE_CURSOR); break;
				case eNE: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
				case eNN: cursor = new Cursor(Cursor.N_RESIZE_CURSOR); break;
				default: break;
			}			
		}		
		return cursor;
	}
}

