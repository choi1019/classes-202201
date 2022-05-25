package frames;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;
	
	public MainFrame() {
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		this.toolBar = new ToolBar();
		contentPane.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new DrawingPanel();
		contentPane.add(this.drawingPanel, BorderLayout.CENTER);
		
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);
	}
	
	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}

}
