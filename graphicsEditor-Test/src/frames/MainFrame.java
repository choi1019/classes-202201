package frames;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;
	
	public MainFrame() {
		// attributes
		this.setSize(400,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());		
		
		this.toolBar = new ToolBar();
		contentPane.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new DrawingPanel();
		contentPane.add(this.drawingPanel, BorderLayout.CENTER);
		
		// association
		this.toolBar.associate(this.drawingPanel);
		
	}

	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}
}
