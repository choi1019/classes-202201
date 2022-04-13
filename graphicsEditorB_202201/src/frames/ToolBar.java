package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.ETools;

public class ToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	private JRadioButton retangleTool;
	private JRadioButton ovalTool;
	private JRadioButton lineTool;
	private JRadioButton polygonTool;
	
	private DrawingPanel drawingPanel;
	
	public ToolBar() {

		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		for (ETools eTool: ETools.values()) {
			this.retangleTool = new JRadioButton("rectangle");
			this.retangleTool.setActionCommand(ETools.eRectanble.name());
			this.retangleTool.addActionListener(actionHandler);
			this.add(this.retangleTool);		
			buttonGroup.add(this.retangleTool);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.ovalTool.doClick();	
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}	
	}
}
