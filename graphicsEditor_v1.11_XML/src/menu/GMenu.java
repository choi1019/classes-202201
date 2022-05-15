package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import frame.GDrawingPanel;
import global.GConstants.EMenuItem;

public class GMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	// association
	private GDrawingPanel drawingPanel;
	private ActionHandler actionHandler;
	private EMenuItem eMenuItems[];
	
	public GMenu(EMenuItem eMenuItems[]) {
		this.eMenuItems = eMenuItems;
	}
	
	public void initialize(GDrawingPanel drawingPanel) {
		this.actionHandler = new ActionHandler();
		
		for (EMenuItem eMenuItem: eMenuItems) {
			Object object = eMenuItem.getMenuItem();
			if (object instanceof JMenuItem) {
				JMenuItem menuItem = (JMenuItem)object;
				menuItem.setText(eMenuItem.getLabel());
				menuItem.setAccelerator(eMenuItem.getAccelerator());
				menuItem.setToolTipText(eMenuItem.getToolTipText());
				menuItem.setActionCommand(eMenuItem.getActionCommand());
				menuItem.addActionListener(actionHandler);
				this.add(menuItem);
			} else if (object instanceof JSeparator) {
				JSeparator separtor = (JSeparator)object;
				this.add(separtor);
			}
		}	
		this.drawingPanel = drawingPanel;
	}
	
	protected GDrawingPanel getDrawingPanel() {
		return this.drawingPanel;
	}
	
	private void invokeMethod(String name) {
			try {
				this.getClass().getMethod(name).invoke(this);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			invokeMethod(event.getActionCommand());
		}
	}
}
