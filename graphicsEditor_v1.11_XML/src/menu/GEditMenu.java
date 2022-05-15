package menu;
import frame.GDrawingPanel;
import global.GConstants.EEditMenuItem;

public class GEditMenu extends GMenu {
	private static final long serialVersionUID = 1L;

	public GEditMenu() {
		super(EEditMenuItem.values());
	}

	public void initialize(GDrawingPanel drawingPanel) {
		super.initialize(drawingPanel);
	}
	
	public void group() {
		this.getDrawingPanel().group();
	}
	public void unGroup() {
		this.getDrawingPanel().unGroup();		
	}
	public void cut() {
		this.getDrawingPanel().cut();
	}
	public void copy() {
		this.getDrawingPanel().copy();		
	}
	public void paste() {
		this.getDrawingPanel().paste();		
	}
	public void delete() {
		this.getDrawingPanel().delete();		
	}
	public void redo() {
		this.getDrawingPanel().redo();		
	}
	public void undo() {
		this.getDrawingPanel().undo();		
	}

}
