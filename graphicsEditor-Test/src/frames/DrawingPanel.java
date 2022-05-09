package frames;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import global.Contants.EDrawingStyle;
import global.Contants.ETools;
import shapes.TShape;
import shapes.TText;

public class DrawingPanel extends JPanel {
	// attribute
	private static final long serialVersionUID = 1L;
 
	// components
	private Vector<TShape> shapes;

	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing
	} 
	private EDrawingState eDrawingState;
	private ETools selectedTool;
	private TShape currentShape;
	
	private BufferedImage bufferedImage;
	private Graphics2D graphics2DBufferedImage;

	public DrawingPanel() {
		// attributes
		this.setBackground(Color.WHITE);
		this.eDrawingState = EDrawingState.eIdle;
		
		// components
		this.shapes = new Vector<TShape>();
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
		
		KeyHandler keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
	}

	public void initialize() {
		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());		
		this.graphics2DBufferedImage = (Graphics2D)(this.bufferedImage.getGraphics());
		
		this.graphics2DBufferedImage.setColor(this.getBackground());
		this.graphics2DBufferedImage.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		this.graphics2DBufferedImage.setColor(this.getForeground());
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;		
	}
	
	public void paint(Graphics graphics) {
		super.paint(graphics);
//		for(TShape shape: this.shapes) {
//			shape.draw(graphics2DBufferedImage);		
//		}
		graphics.drawImage(this.bufferedImage, 0, 0, this);
	}
	
	private void prepareDrawing(int x, int y) {
		this.currentShape = this.selectedTool.newInstance();
		this.currentShape.setOrigin(x, y);
		this.currentShape.draw(graphics2DBufferedImage);	
	}	
	private void keepDrawing(int x, int y) {
		// erase
		this.currentShape.draw(graphics2DBufferedImage);		
		// draw
		this.currentShape.resize(x, y);
		this.currentShape.draw(graphics2DBufferedImage);		
		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
	}
	private boolean continueDrawing(int x, int y) {
		return this.currentShape.addPoint(x, y);
	}
	private void finishDrawing(int x, int y) {
		this.shapes.add(this.currentShape);
		if (this.currentShape instanceof TText) {
			this.requestFocusInWindow();
		}
	}
	
	public void drawText(char keyChar) {
		this.currentShape.drawText(keyChar, graphics2DBufferedImage);
		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
	}
	
	
	private class KeyHandler implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println(e.getKeyChar());
			drawText(e.getKeyChar());
		}		
		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}		
	}
		
	private class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					lBUttonClick(e);
					System.out.println("lButtonClicked");
				} else if (e.getClickCount() == 2) {
					lBUttonDoubleClick(e);
					System.out.println("lButtonDoubleClicked");
				}
			}
		}
		
		private void lBUttonClick(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					prepareDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;
				}
			} else if (eDrawingState == EDrawingState.eNPointDrawing) {
				if (!continueDrawing(e.getX(), e.getY())) {
					finishDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			}
		}	
		private void lBUttonDoubleClick(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {	
				keepDrawing(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					prepareDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {	
				keepDrawing(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				finishDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}
	}

}
