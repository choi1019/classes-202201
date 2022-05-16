package global;


import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;

public class Constants {
	
	public enum EDrawingStyle {
		e2Point,
		eNPoint
	}
	
	public enum ETools { 
		eSelection("����", new TSelection(), EDrawingStyle.e2Point),
		eRectangle("�׸�", new TRectangle(), EDrawingStyle.e2Point),
		eOval("���׶��", new TOval(), EDrawingStyle.e2Point),
		eLine("����", new TLine(), EDrawingStyle.e2Point),
		ePolygon("������", new TPolygon(), EDrawingStyle.eNPoint);
		
		private String label;
		private TShape tool;
		private EDrawingStyle eDrawingStyle;
		
		private ETools(String label, TShape tool, EDrawingStyle eDrawingStyle) {
			this.label = label;
			this.tool = tool;
			this.eDrawingStyle = eDrawingStyle;
		}
		public String getLabel() {
			return this.label;
		}
		public TShape newShape() {
			return this.tool.clone();
		}
		public EDrawingStyle getDrawingStyle() {
			return this.eDrawingStyle;
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
