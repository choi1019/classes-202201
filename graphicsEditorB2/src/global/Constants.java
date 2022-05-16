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
		eSelection("선택", new TSelection(), EDrawingStyle.e2Point),
		eRectangle("네모", new TRectangle(), EDrawingStyle.e2Point),
		eOval("동그라미", new TOval(), EDrawingStyle.e2Point),
		eLine("라인", new TLine(), EDrawingStyle.e2Point),
		ePolygon("폴리곤", new TPolygon(), EDrawingStyle.eNPoint);
		
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
