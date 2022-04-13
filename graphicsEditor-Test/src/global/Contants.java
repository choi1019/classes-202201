package global;

import shapes.TShape;
import shapes.TRectangle;
import shapes.TOval;
import shapes.TLine;
import shapes.TPolygon;
import shapes.TText;

public class Contants {
	
	public enum EDrawingStyle {
		e2PointDrawing,
		eNPointDrawing
	}
	
	public enum ETools {
		eRectangle(new TRectangle(), EDrawingStyle.e2PointDrawing),
		eOval(new TOval(), EDrawingStyle.e2PointDrawing),
		eLine(new TLine(), EDrawingStyle.e2PointDrawing),
		ePolygon(new TPolygon(), EDrawingStyle.eNPointDrawing),
		eText(new TText(), EDrawingStyle.e2PointDrawing);
		
		private TShape tool;
		private EDrawingStyle eDrawingStyle;
		
		private ETools(TShape tool, EDrawingStyle eDrawingStyle) {
			this.tool = tool;
			this.eDrawingStyle = eDrawingStyle;
		}
		
		public TShape newInstance() {
			return tool.clone();
		}
		public EDrawingStyle getDrawingStyle() {
			return eDrawingStyle;
		}
	}
}
