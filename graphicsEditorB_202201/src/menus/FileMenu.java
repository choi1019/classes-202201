package menus;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem closeItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem printItem;
	private JMenuItem quitItem;
	
	public FileMenu(String title) {
		super(title);
		
		this.newItem = new JMenuItem("new");
		this.add(this.newItem);
		
		this.openItem = new JMenuItem("open");
		this.add(this.openItem);
		
		this.closeItem = new JMenuItem("close");
		this.add(this.closeItem);
		
		this.saveItem = new JMenuItem("save");
		this.add(this.saveItem);
		
		this.saveAsItem = new JMenuItem("saveAs");
		this.add(this.saveAsItem);
		
		this.printItem = new JMenuItem("print");
		this.add(this.printItem);
		
		this.quitItem = new JMenuItem("quit");
		this.add(this.quitItem);
	}

}
