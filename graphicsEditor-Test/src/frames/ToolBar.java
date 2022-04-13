package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Contants.ETools;

public class ToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private JRadioButton rectangleTool;
	private JRadioButton ovalTool;
	private JRadioButton lineTool;
	private JRadioButton ploygonTool;
	private JRadioButton textTool;
	
	// associations
	private DrawingPanel drawingPanel;
	
	public ToolBar() {
		// components
		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		this.rectangleTool = new JRadioButton("rectangle");
		this.rectangleTool.setActionCommand(ETools.eRectangle.name());
		this.rectangleTool.addActionListener(actionHandler);
		this.add(this.rectangleTool);
		buttonGroup.add(this.rectangleTool);
		
		this.ovalTool = new JRadioButton("oval");
		this.ovalTool.setActionCommand(ETools.eOval.name());
		this.ovalTool.addActionListener(actionHandler);
		this.add(this.ovalTool);
		buttonGroup.add(this.ovalTool);
		
		this.lineTool = new JRadioButton("line");
		this.lineTool.setActionCommand(ETools.eLine.name());
		this.lineTool.addActionListener(actionHandler);
		this.add(this.lineTool);
		buttonGroup.add(this.lineTool);
		
		this.ploygonTool = new JRadioButton("polygon");
		this.ploygonTool.setActionCommand(ETools.ePolygon.name());
		this.ploygonTool.addActionListener(actionHandler);
		this.add(this.ploygonTool);
		buttonGroup.add(this.ploygonTool);
		
		this.textTool = new JRadioButton("text");
		this.textTool.setActionCommand(ETools.eText.name());
		this.textTool.addActionListener(actionHandler);
		this.add(this.textTool);
		buttonGroup.add(this.textTool);
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void initialize() {
		this.rectangleTool.doClick();
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}		
	}


}
