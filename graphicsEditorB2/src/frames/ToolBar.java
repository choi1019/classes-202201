package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.ETools;

public class ToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private DrawingPanel drawingPanel;
	
	public ToolBar() {

		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		for (ETools eTool: ETools.values()) {
			JRadioButton toolButton = new JRadioButton(eTool.getLabel());
			toolButton.setActionCommand(eTool.name());
			toolButton.addActionListener(actionHandler);
			this.add(toolButton);		
			buttonGroup.add(toolButton);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
<<<<<<< HEAD
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eRectangle.ordinal());
=======
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
>>>>>>> branch '202201' of https://github.com/choi1019/graphicsEditor.git
		defaultButton.doClick();	
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}	
	}
}
