package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.GConstants.EToolBarButton;

public class GToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;
	// associations
	private GDrawingPanel drawingPanel;
    
    public GToolBar() {
    	ActionHandler actionHandler = new ActionHandler();
    	
		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		
		for (EToolBarButton eToolBarButton: EToolBarButton.values()) {
			JRadioButton button = new JRadioButton();
			button.setIcon(new ImageIcon(eToolBarButton.getImageFileName()));
			button.setSelectedIcon(new ImageIcon(eToolBarButton.getImageSltFile()));
			button.setToolTipText(eToolBarButton.getToolTipText());
			button.addActionListener(actionHandler);
			button.setActionCommand(eToolBarButton.toString());
			
			this.add(button);
			group.add(button);
		}
   }

	public void initialize(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		((JRadioButton)(this.getComponent(EToolBarButton.eRectangle.ordinal()))).doClick();
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			drawingPanel.setSelectedTool(
					EToolBarButton.valueOf(event.getActionCommand()).getSelectedTool());
		}		
	}

}
