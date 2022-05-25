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
		eSelection("선택", new TSelection()),
		eRectanble("네모", new TRectangle()),
		eOval("동그라미", new TOval()),
		eLine("라인", new TLine()),
		ePolygon("다각형", new TPolygon());
		
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
		eSelection("선택", new TSelection(), ETransformationStyle.e2Point),
		eRectanble("네모", new TRectangle(), ETransformationStyle.e2Point),
		eOval("동그라미", new TOval(), ETransformationStyle.e2Point),
		eLine("라인", new TLine(), ETransformationStyle.e2Point),
		ePolygon("다각형", new TPolygon(), ETransformationStyle.eNPoint);
		
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
		eNew("새로 만들기"),
		eOpen("열기"),
		eClose("닫기"),
		eSave("저장"),
		eSaveAs("다른이름으로"),
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
