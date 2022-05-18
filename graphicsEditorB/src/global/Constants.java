package global;


import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;

public class Constants {
	
	public enum ETransformationStyle {
		e2PTransformation,
		eNPTransformation
	}
	public enum ETools { 
		eSelection("선택", new TSelection(), ETransformationStyle.e2PTransformation),
		eRectanble("네모", new TRectangle(), ETransformationStyle.e2PTransformation),
		eOval("동그라미", new TOval(), ETransformationStyle.e2PTransformation),
		eLine("라인", new TLine(), ETransformationStyle.e2PTransformation),
		ePolygon("폴리곤", new TPolygon(), ETransformationStyle.eNPTransformation);
		
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
		}
	}
	
	public enum EFileMenu {
		eNew("새로만들기"),
		eOpen("열기"),
		eSave("저장하기"),
		eSaveAs("다른이름으로"),
		eClose("닫기"),
		ePrint("프린트"),
		eQuit("종료");
		
		private String label;
		private EFileMenu(String label) {
			this.label = label;
		}
		public String getLabel() {
			return this.label;
		}
	}
}
