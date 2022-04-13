package global;


import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TShape;

public class Constants {
	
	public enum ETools { 
		eRectanble(new TRectangle()),
		eOval(new TOval()),
		eLine(new TLine()),
		ePolygon(new TPolygon());
		
		private TShape tool;
		private ETools(TShape tool) {
			this.tool = tool;
		}
		public TShape newShape() {
			return this.tool.clone();
		}
	}
}
