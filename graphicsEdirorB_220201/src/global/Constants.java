package global;


import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TShape;

public class Constants {
	
	public enum ETools { 
		eOval("���׶��", new TOval()),
		eRectanble("�׸�", new TRectangle()),
		eLine("����", new TLine()),
		eLine1("����1", new TLine()),
		ePolygon("������", new TPolygon());
		
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
		eNew("���θ����"),
		eOpen("����"),
		eSave("�����ϱ�"),
		eSaveAs("�ٸ��̸�����"),
		eClose("�ݱ�"),
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
