package frame;

import global.GConstants;

public class GMain {
	public static void main(String[] args) {
		GConstants constants = new GConstants();
		
		constants.readFromFile("config.xml");
		
		GMainFrame mainframe = new GMainFrame();
		mainframe.setVisible(true);
		
		mainframe.initialize();
	}
}
