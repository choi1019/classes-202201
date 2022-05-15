package frame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import global.GConstants.EMenu;
import menu.GFileMenu;

public class GMenuBar extends JMenuBar {
	// attributes
	private static final long serialVersionUID = 1L;

	// associations
	private GDrawingPanel drawingPanel;
	
	public GMenuBar() {
	}

	public void initialize(GDrawingPanel drawingPanel) {
		// components
		for (EMenu eMenu: EMenu.values()) {
			JMenu menu = eMenu.getMenu();
			menu.setText(eMenu.getLabel());
			this.add(menu);
		}
		
		this.drawingPanel = drawingPanel;
		
		for (EMenu eMenu: EMenu.values()) {
			eMenu.getMenu().initialize(this.drawingPanel);
		}
	}

	public void exit() {
		((GFileMenu)(EMenu.eFileMenu.getMenu())).exit();
	}
}
