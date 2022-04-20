package global;

import shapes.TShape;
import shapes.TRectangle;
import shapes.TOval;
import shapes.TLine;
import shapes.TPolygon;

public class Constants {
	
	public enum ETools { 
		eOval("동그라미", new TOval()),
		eRectanble("네모", new TRectangle()),
		eRectanble1("네모1", new TRectangle()),
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
