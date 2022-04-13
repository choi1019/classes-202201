package global;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import menu.GEditMenu;
import menu.GFileMenu;
import menu.GGraphicsMenu;
import menu.GMenu;
import shape.GEllipse;
import shape.GLine;
import shape.GPolygon;
import shape.GRectangle;
import shape.GSelect;
import shape.GShapeManager;
import shape.GTextArea;

public class GConstants extends DefaultHandler {
	
	public GConstants() {
	}
	
	public void readFromFile(String fileName) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Load the input XML document, parse it and return an instance of the
			// Document class.
			File file = new File(fileName);
			Document document = builder.parse(file);
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i=0; i<nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (node.getNodeName().equals(EMainFrame.class.getSimpleName())) {
						EMainFrame.setValues(node);
					} else if (node.getNodeName().equals(EMenu.class.getSimpleName())) {
						EMenu.setValues(node);
					} else if (node.getNodeName().equals(EFileMenuItem.class.getSimpleName())) {
						EFileMenuItem.setValue(node);
					} else if (node.getNodeName().equals(EEditMenuItem.class.getSimpleName())) {
						EEditMenuItem.setValue(node);					
					} else if (node.getNodeName().equals(EGraphicsMenuItem.class.getSimpleName())) {
						EGraphicsMenuItem.setValue(node);					
					} else if (node.getNodeName().equals(EToolBarButton.class.getSimpleName())) {
						EToolBarButton.setValue(node);						
					} 
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public enum EMainFrame {
		eX(0),
		eY(0),
		eW(0),
		eH(0);
		
		private int value;
		private EMainFrame(int value) {
			this.value = value;
		}
		public static void setValues(Node node) {
			for (EMainFrame eMainFrame: EMainFrame.values()) {
				Node attribute = node.getAttributes().getNamedItem(eMainFrame.name());
				eMainFrame.value = Integer.parseInt(attribute.getNodeValue());				
			}
		}
		public int getValue() {
			return this.value;
		}
	}

	public enum EMenu {
		eFileMenu(new GFileMenu(), null),
		eEditMenu(new GEditMenu(), null),
		eGraphicsMenu(new GGraphicsMenu(), null);
		
		private GMenu menu;
		private String label;
		
		private EMenu(GMenu menu, String label) {
			this.menu = menu;
			this.label = label;
		}
		public static void setValues(Node node) {
			for (int i=0; i<node.getChildNodes().getLength(); i++) {
				try {
					Node element = node.getChildNodes().item(i);
					if (element.getNodeType() == Node.ELEMENT_NODE) {
						EMenu eMenu = EMenu.valueOf(element.getNodeName());
						eMenu.label = element.getAttributes().getNamedItem("label").getNodeValue();
					}
				} catch(IllegalArgumentException e) {
					e.printStackTrace();					
				}
			}
		}
		public GMenu getMenu() {
			return this.menu;
		}
		public String getLabel() {
			return this.label;
		}
	}
	
	public interface EMenuItem {
		public Object getMenuItem();
		public String getLabel();
		public KeyStroke getAccelerator();
		public String getToolTipText();
		public String getActionCommand();
	}
	
	public enum EFileMenuItem implements EMenuItem {
		eNew(new JMenuItem(),  null, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK), "", "nnew"),
		eOpen(new JMenuItem(), null, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK), "", "open"),
		eSave(new JMenuItem(), null, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK), "", "save"),
		eSaveAs(new JMenuItem(), null, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_MASK), "", "saveAs"),
		eClose(new JMenuItem(), null, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK), "", "close"),
		eSeparator1(new JSeparator(), null, null, null, null),
		ePrint(new JMenuItem(), null, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK), "", "print"),
		eSeparator2(new JSeparator(), null, null, null, null),
		eExit(new JMenuItem(), null, KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK), "", "exit");
		
		static private String defaultPathName;
		
		private Object menuItem;
		private String label;
		private KeyStroke keyStroke;
		private String toolTipText;
		private String actionCommand;
		
		private EFileMenuItem(Object menuItem, String label, KeyStroke keyStroke, String toolTipText, String actionCommand) {
			this.menuItem = menuItem;
			this.label = label;
			this.keyStroke = keyStroke;
			this.toolTipText = toolTipText;
			this.actionCommand = actionCommand;
		}
		
		public static void setValue(Node node) {
			defaultPathName = node.getAttributes().getNamedItem("defaultPathName").getNodeValue();
			
			for (int i=0; i<node.getChildNodes().getLength(); i++) {
				try {
					Node element = node.getChildNodes().item(i);
					if (element.getNodeType() == Node.ELEMENT_NODE) {
						EFileMenuItem eFileMenuItem = EFileMenuItem.valueOf(element.getNodeName());
						eFileMenuItem.label = element.getAttributes().getNamedItem("label").getNodeValue();
						eFileMenuItem.toolTipText = element.getAttributes().getNamedItem("toolTipText").getNodeValue();
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}

		public static String getDefaultPathName() {
			return defaultPathName;
		}
		
		public Object getMenuItem() {
			return this.menuItem;
		}
		public String getLabel() {
			return this.label; 
		}
		public KeyStroke getAccelerator() {
			return this.keyStroke;
		}
		public String getToolTipText() {
			return this.toolTipText; 
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
	public enum EEditMenuItem implements EMenuItem {
		eReDo(new JMenuItem("redo"), null, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), "", "redo"),
		eUnDo(new JMenuItem("undo"), null, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), "", "undo"),
		eSeparator1(new JSeparator(), null, null, null, null),
		eCut(new JMenuItem("cut"), null, KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK), "", "cut"),
		eCopy(new JMenuItem("copy"), null, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK), "", "copy"),
		ePaste(new JMenuItem("paste"), null, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK), "", "paste"),
		eDelete(new JMenuItem("delete"), null, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "", "delete"),
		eSeparator2(new JSeparator(), null, null, null, null),
		eGroup(new JMenuItem("group"), null, KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK), "", "group"),
		eUnGroup(new JMenuItem("unGroup"), null, KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK), "", "unGroup");
	
		private Object menuItem;
		private String label;
		private KeyStroke keyStroke;
		private String toolTipText;
		private String actionCommand;
		
		private EEditMenuItem(Object menuItem, String label, KeyStroke keyStroke, String toolTipText, String actionCommand) {
			this.menuItem = menuItem;
			this.label = label;
			this.keyStroke = keyStroke;
			this.toolTipText = toolTipText;
			this.actionCommand = actionCommand;
		}
		
		public static void setValue(Node node) {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				try {
					Node element = node.getChildNodes().item(i);
						if (element.getNodeType() == Node.ELEMENT_NODE) {
						EEditMenuItem eEditMenuItem = EEditMenuItem.valueOf(element.getNodeName());
						eEditMenuItem.label = element.getAttributes().getNamedItem("label").getNodeValue();
						eEditMenuItem.toolTipText = element.getAttributes().getNamedItem("toolTipText").getNodeValue();
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}

		public Object getMenuItem() {
			return this.menuItem;
		}
		public String getLabel() {
			return this.label; 
		}
		public KeyStroke getAccelerator() {
			return this.keyStroke;
		}
		public String getToolTipText() {
			return this.toolTipText; 
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
	public enum EGraphicsMenuItem implements EMenuItem {
		eFontStyle(new JMenuItem(), null, null, "", "setFontStyle"),
		eSeparator1(new JSeparator(), null, null, null, null),
		elineStyle(new JMenuItem(), null, null, "", "setLineStyle"),
		eSeparator2(new JSeparator(), null, null, null, null),
		eLineColor(new JMenuItem(), null, null, "", "setLineColor"),
		eFillColor(new JMenuItem(), null, null, "", "setFillColor");
		
		private Object menuItem;
		private String label;
		private KeyStroke keyStroke;
		private String toolTipText;
		private String actionCommand;
		
		private EGraphicsMenuItem(Object menuItem, String label, KeyStroke keyStroke, String toolTipText, String actionCommand) {
			this.menuItem = menuItem;
			this.label = label;
			this.keyStroke = keyStroke;
			this.toolTipText = toolTipText;
			this.actionCommand = actionCommand;
		}
		public static void setValue(Node node) {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				try {
					Node element = node.getChildNodes().item(i);
					if (element.getNodeType() == Node.ELEMENT_NODE) {
						EGraphicsMenuItem eGraphicsMenuItem = EGraphicsMenuItem.valueOf(element.getNodeName());
						eGraphicsMenuItem.label = element.getAttributes().getNamedItem("label").getNodeValue();
						eGraphicsMenuItem.toolTipText = element.getAttributes().getNamedItem("toolTipText").getNodeValue();
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		public Object getMenuItem() {
			return this.menuItem;
		}
		public String getLabel() {
			return this.label; 
		}
		public KeyStroke getAccelerator() {
			return this.keyStroke;
		}
		public String getToolTipText() {
			return this.toolTipText; 
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
	public enum EToolBarButton {
		eSelect(new GSelect(), null, null, null),
		eRectangle(new GRectangle(), null, null, null),
		eEllipse(new GEllipse(), null, null, null),
		eLine(new GLine(), null, null, null),
		ePolygon(new GPolygon(), null, null, null),	
		eTextArea(new GTextArea(), null, null, null);

		static private String pathName;
		
		private GShapeManager selectedTool;
		private String label;
		private String toolTipText;
		private String imageFileName;
		
		
		private EToolBarButton(GShapeManager selectedTool, String label, String toolTipText, String imageFileName) {
			this.selectedTool = selectedTool;
			this.label = label;
			this.toolTipText = toolTipText;
			this.imageFileName = imageFileName;
		}
		
		public static void setValue(Node node) {
			pathName = node.getAttributes().getNamedItem("pathName").getNodeValue();
			
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				try {
					Node element = node.getChildNodes().item(i);
					if (element.getNodeType() == Node.ELEMENT_NODE) {
						EToolBarButton eToolBarButton = EToolBarButton.valueOf(element.getNodeName());
						eToolBarButton.label = element.getAttributes().getNamedItem("label").getNodeValue();
						eToolBarButton.toolTipText = element.getAttributes().getNamedItem("toolTipText").getNodeValue();
						eToolBarButton.imageFileName = element.getAttributes().getNamedItem("imageFileName").getNodeValue();
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
			}
		public GShapeManager getSelectedTool() {
			return this.selectedTool;
		}
		public String getLabel() {
			return this.label; 
		}
		public String getToolTipText() {
			return this.toolTipText;
		}
		public String getImageFileName() {
			return pathName + "\\" + this.imageFileName + ".gif";
		}
		public String getImageSltFile() {
			return pathName + "\\" + this.imageFileName + "SLT.gif";
		}
	}
	
	public static final int ANCHOR_W = 10;
	public static final int ANCHOR_H = 10;
	
	public enum EAnchors { 
		N(new Cursor(Cursor.N_RESIZE_CURSOR)), 
		S(new Cursor(Cursor.S_RESIZE_CURSOR)), 
		E(new Cursor(Cursor.E_RESIZE_CURSOR)), 
		W(new Cursor(Cursor.W_RESIZE_CURSOR)), 
		NE(new Cursor(Cursor.NE_RESIZE_CURSOR)), 
		NW(new Cursor(Cursor.NW_RESIZE_CURSOR)), 
		SE(new Cursor(Cursor.SE_RESIZE_CURSOR)), 
		SW(new Cursor(Cursor.SW_RESIZE_CURSOR)), 
		R(new Cursor(Cursor.HAND_CURSOR)),
		M(new Cursor(Cursor.MOVE_CURSOR));

		
		private Cursor cursor;
		
		private EAnchors(Cursor cursor) {
			this.cursor = cursor;
		}
		public Cursor getCursor() {
			return this.cursor;
		}
	}
}
