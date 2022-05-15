package global;


import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;

public class Constants {
	
	public enum ETools { 
		eSelection("선택", new TSelection()),
		eRectanble("네모", new TRectangle()),
		eOval("동그라미", new TOval()),
		eLine("라인", new TLine()),
		ePolygon("폴리곤", new TPolygon());
		
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
