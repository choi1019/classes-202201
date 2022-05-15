package global;

import shapes.TShape;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TOval;
import shapes.TLine;
import shapes.TPolygon;

public class Constants {
	
	public enum ETools { 
		eSelection("����", new TSelection()),
		eRectanble("�׸�", new TRectangle()),
		eOval("���׶��", new TOval()),
		eLine("����", new TLine()),
		ePolygon("�ٰ���", new TPolygon());
		
		private String label;
		private TShape tool;
		
		private ETools(String label, TShape tool) {
			this.label = label;
			this.tool = tool;
		}
		public String getLabel() {
			return this.label;
		}
		public TShape newShape() {
			return this.tool.clone();
		}
	}
	
	public enum EFileMenu {
		eNew("���� �����"),
		eOpen("����"),
		eClose("�ݱ�"),
		eSave("����"),
		eSaveAs("�ٸ��̸�����"),
		ePrint("����Ʈ"),
		eQuit("����");
		
		private String label;
		
		private EFileMenu(String label) {
			this.label = label;
		}
		public String getLabel() {
			return this.label;
		}
	}
}
