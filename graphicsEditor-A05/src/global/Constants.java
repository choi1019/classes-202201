package global;

import shapes.TShape;
import transformers.Drawer;
import transformers.Mover;
import transformers.Resizer;
import transformers.Rotator;
import transformers.Transformer;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TOval;
import shapes.TLine;
import shapes.TPolygon;

public class Constants {
	
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
		public ETransformationStyle getTransformationStyle() {
			return this.eTransformationStyle;
		}
	}
	
	public enum ETransformers {
		eDrawer(new Drawer()),
		eMover(new Mover()),
		eResizer(new Resizer()),
		eRotator(new Rotator());
		
		private Transformer transformer;
		private ETransformers(Transformer transformer) {
			this.transformer = transformer;			
		}
		public Transformer getTransformer() {
			return this.transformer;
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
