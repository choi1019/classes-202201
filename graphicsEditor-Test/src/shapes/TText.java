package shapes;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TText extends TShape {
	private int x, y, width, height;
	private String text;
	
	public TText() {
		this.text = new String();
	}
	
	public TShape clone() {
		return new TText();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
	}
	
	public void resize(int x, int y) {
		this.width = x - this.x;
		this.height = y - this.y;		
	}
	public void draw(Graphics2D graphics) {
		graphics.drawRect(this.x, this.y, this.width, this.height);
		graphics.drawString(this.text, x, y);
	}
	
	public void drawText(char keyChar, Graphics2D graphics) {
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int translatedY = y + fontMetrics.getAscent();
		
		graphics.drawString(this.text, x, translatedY);
		this.text = this.text + keyChar;
		
		Font font = new Font ("font/NanumBarunGothic.ttf", Font.PLAIN, 20);
		graphics.setFont(font);
		graphics.drawString(this.text, x, translatedY);
	}
}

