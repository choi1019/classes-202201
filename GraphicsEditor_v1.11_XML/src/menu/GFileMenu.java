package menu;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import frame.GDrawingPanel;
import global.GConstants.EFileMenuItem;

public class GFileMenu extends GMenu {
	
	private static final long serialVersionUID = 1L;
	
	private File dir; 
	private File file; 
 	
	public GFileMenu() {
		super(EFileMenuItem.values());
	} 
	
	@Override
	public void initialize(GDrawingPanel drawingPanel) {
		super.initialize(drawingPanel);
		
		this.dir = new File(EFileMenuItem.getDefaultPathName());
		this.file = null;
	}
	
	private void readObject() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(this.file)));
			Object object = objectInputStream.readObject();
			this.getDrawingPanel().setShapeVector(object);
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	private void writeObject() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(file)));
			objectOutputStream.writeObject(this.getDrawingPanel().getShapeVector());
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private boolean checkSave() {
		boolean bCancel = false;
		int reply = JOptionPane.NO_OPTION;
		if (this.getDrawingPanel().isUpdated()) {
			reply = JOptionPane.showConfirmDialog(this.getDrawingPanel(), "변경내용을 저장 할까요?");
			if (reply == JOptionPane.CANCEL_OPTION) {	
				bCancel = true;
			} 
		} 		
		if (!bCancel) {
			if (reply == JOptionPane.OK_OPTION) {
				bCancel = this.save();
			}
		}
		return bCancel;
	}
	
	public void nnew() {
		boolean bCancel = this.checkSave();
		if (!bCancel) {		
			this.getDrawingPanel().initialize();
		}
	}
	
	public void open() {		
		boolean bCancel = this.checkSave();
		if (!bCancel) {
			JFileChooser chooser = new JFileChooser(this.dir);
			chooser.setSelectedFile(file);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "gvs");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this.getDrawingPanel());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.dir = chooser.getCurrentDirectory();
				this.file = chooser.getSelectedFile();
				this.getDrawingPanel().initialize();
				this.readObject();
			}
		}
	}

	public boolean save() {
		boolean bCancel = false;
		if (this.file == null) {
			bCancel = this.saveAs();			
		} else {
			this.writeObject();
			this.getDrawingPanel().setUpdated(false);
		}
		return bCancel;
	}
	
	public boolean saveAs() {
		boolean bCancel = false;
		
		JFileChooser chooser = new JFileChooser(this.dir);
		chooser.setSelectedFile(file);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "gvs");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this.getDrawingPanel());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.dir = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			this.writeObject();
			this.getDrawingPanel().setUpdated(false);
		} else {
			bCancel = true;
		}		
		return bCancel;
	}
	
	public void close() {
		boolean bCancel = this.checkSave();
		if (!bCancel) {		
			this.getDrawingPanel().initialize();
		}
	}

	public void exit() {
		boolean bCancel = this.checkSave();
		if (!bCancel) {		
			System.exit(0);
		}
	}
	
	public void print() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        PageFormat pageFormat = new PageFormat();
        printerJob.setPrintable(this.getDrawingPanel(), pageFormat);
        boolean ok = printerJob.printDialog(printRequestAttributeSet);
        if (ok) {
            try {
            	printerJob.print(printRequestAttributeSet);
            } catch (PrinterException ex) {
             /* The job did not successfully complete */
            }
        }
	}
}
