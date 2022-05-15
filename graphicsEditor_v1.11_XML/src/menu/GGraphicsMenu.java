package menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import frame.GDrawingPanel;
import global.GConstants.EGraphicsMenuItem;

public class GGraphicsMenu extends GMenu {
	private static final long serialVersionUID = 1L;

	public GGraphicsMenu() {
		super(EGraphicsMenuItem.values());
	}

	public void initialize(GDrawingPanel drawingPanel) {
		super.initialize(drawingPanel);
	}
	
	public void setFontStyle() {
//		Font font = this.getGraphics().getFont();
//		FontChooserPanel panel = new FontChooserPanel(font);
//	    int result = JOptionPane.showConfirmDialog(this, panel, "Font_Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//	    if (result == JOptionPane.OK_OPTION) {
//	    	StrokeSample.setStroke(panel.getSelectedStroke());
//	    }
	}
	
	public void setLineStyle() {
		Stroke stroke = new BasicStroke();
		StrokeSample StrokeSample = new StrokeSample(stroke);
		StrokeSample[] strokeSamples = new StrokeSample[3];

		strokeSamples[0] = new StrokeSample(stroke);
		strokeSamples[1] = new StrokeSample(stroke);
		strokeSamples[2] = new StrokeSample(stroke);
	
		StrokeChooserPanel panel = new StrokeChooserPanel(StrokeSample, strokeSamples);
	    int result = JOptionPane.showConfirmDialog(this, panel, "Stroke_Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	    if (result == JOptionPane.OK_OPTION) {
	    	StrokeSample.setStroke(panel.getSelectedStroke());
	    }
	}
	
	public void setLineColor() {
		Color lineColor = JColorChooser.showDialog(
				this.getDrawingPanel(), "Choose Line Color", this.getDrawingPanel().getForeground());
		if (lineColor != null) {
			this.getDrawingPanel().setLineColor(lineColor);
		}
	}
	public void setFillColor() {
		Color fillColor = JColorChooser.showDialog(
				this.getDrawingPanel(), "Choose Fill Color", this.getDrawingPanel().getForeground());
		if (fillColor != null) {
			this.getDrawingPanel().setFillColor(fillColor);
		}		
	}
}
