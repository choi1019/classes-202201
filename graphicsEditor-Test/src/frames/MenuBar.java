package frames;
import javax.swing.JMenuBar;

import menus.EditMenu;
import menus.FileMenu;

public class MenuBar extends JMenuBar {
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private FileMenu fileMenu;
	private EditMenu editMenu;
	
	public MenuBar() {
		
		// components
		this.fileMenu = new FileMenu("ÆÄÀÏ");
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("Edit");
		this.add(this.editMenu);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
