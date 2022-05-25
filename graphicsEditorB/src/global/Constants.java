package global;


import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;

public class Constants {
	
<<<<<<< HEAD
	public enum ETools { 
		eSelection("����", new TSelection()),
		eRectanble("�׸�", new TRectangle()),
		eOval("���׶��", new TOval()),
		eLine("����", new TLine()),
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
=======
	public enum ETransformationStyle {
		e2PTransformation,
		eNPTransformation
	}
	public enum ETools { 
		eSelection("����", new TSelection(), ETransformationStyle.e2PTransformation),
		eRectanble("�׸�", new TRectangle(), ETransformationStyle.e2PTransformation),
		eOval("���׶��", new TOval(), ETransformationStyle.e2PTransformation),
		eLine("����", new TLine(), ETransformationStyle.e2PTransformation),
		ePolygon("������", new TPolygon(), ETransformationStyle.eNPTransformation);
		
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
		public ETransformationStyle getTransformationStyle() {
			return eTransformationStyle;
>>>>>>> branch '202201' of https://github.com/choi1019/graphicsEditor.git
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
