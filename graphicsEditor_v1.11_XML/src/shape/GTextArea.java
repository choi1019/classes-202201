package shape;

import java.awt.Rectangle;
import java.awt.TextArea;

public class GTextArea extends GShapeManager {
	private static final long serialVersionUID = 1L;
	// attributes
	private Rectangle rectangle;
	private TextArea textArea;
	
	public GTextArea() {
		super(new Rectangle(0, 0 , 0, 0));
		this.rectangle = (Rectangle)this.getShape();
		this.textArea = new TextArea();
	}
	public GTextArea newInstance() {
		return new GTextArea();
	}
	
	public TextArea getTextArea() { return this.textArea; }
	
	public void setOrigin(int x, int y) {
		this.rectangle.setFrame(x, y, 
				this.rectangle.getWidth(), this.rectangle.getHeight());
		this.textArea.setLocation(x, y);
	}
	public void movePoint(int newX, int newY) {
		double w = newX - this.rectangle.getX();
		double h = newY - this.rectangle.getY();
		this.rectangle.setFrame(this.rectangle.getX(), this.rectangle.getY(), w, h);
		this.textArea.setBounds(this.rectangle);
	}
}
