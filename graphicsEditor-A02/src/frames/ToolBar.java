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
	JRadioButton defaultButton;
	
	public ToolBar() {

		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		for (ETools eTool: ETools.values()) {
			JRadioButton drawingTool = new JRadioButton(eTool.getLabel());
			drawingTool.setActionCommand(eTool.name());
			drawingTool.addActionListener(actionHandler);
			this.add(drawingTool);		
			buttonGroup.add(drawingTool);
		}
		
		this.defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.drawingPanel.setDefaultTool(defaultButton);

	}
	public void initialize() {
		defaultButton.doClick();
	}
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}	
	}


}
