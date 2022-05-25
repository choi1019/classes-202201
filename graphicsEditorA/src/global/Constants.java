package global;

import shapes.TShape;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TOval;
import shapes.TLine;
import shapes.TPolygon;

public class Constants {
	
<<<<<<< HEAD
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
=======
	public enum ETransformationStyle {
		e2Point, 
		eNPoint
	}
	public enum ETools { 
		eSelection("����", new TSelection(), ETransformationStyle.e2Point),
		eRectanble("�׸�", new TRectangle(), ETransformationStyle.e2Point),
		eOval("���׶��", new TOval(), ETransformationStyle.e2Point),
		eLine("����", new TLine(), ETransformationStyle.e2Point),
		ePolygon("�ٰ���", new TPolygon(), ETransformationStyle.eNPoint);
		
		private String label;
		private TShape tool;
		private ETransformationStyle eTransformationStyle;
		
		private ETools(String label, TShape tool, ETransformationStyle eTransformationStyle) {
			this.label = label;
			this.tool = tool;
			this.eTransformationStyle = eTransformationStyle;
		}
		public String getLabel() {
			return this.label;
		}
		public TShape newShape() {
			return this.tool.clone();
		}
		public ETransformationStyle getETransformationStyle() {
			return this.eTransformationStyle;
>>>>>>> branch '202201' of https://github.com/choi1019/graphicsEditor.git
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
