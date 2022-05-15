package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import global.GConstants.EMainFrame;

public class GMainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;
	// components
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;

	public GMainFrame() {
		// attributes
		this.setLocation(EMainFrame.eX.getValue(), EMainFrame.eY.getValue());
		this.setSize(EMainFrame.eW.getValue(), EMainFrame.eH.getValue());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		// components
		WindowsHandler windowsHandler = new WindowsHandler();
		this.addWindowListener(windowsHandler);
		
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new GToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
	}

	public void initialize() {	
		// initialize
		this.drawingPanel.initialize();	
		this.menuBar.initialize(this.drawingPanel);
		this.toolBar.initialize(this.drawingPanel);
	}
	
	private class WindowsHandler implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
		}

		@Override
		public void windowClosing(WindowEvent event) {
			menuBar.exit();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
		}
		
	}
}
