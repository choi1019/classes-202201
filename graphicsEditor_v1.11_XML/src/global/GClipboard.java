package global;

import java.util.Vector;

import shape.GShapeManager;

public class GClipboard {
	
	private Vector<Vector<GShapeManager>> elementVector;
	private int maxSize;
	private int top;
	private int start;
	private int current;
	
	public GClipboard() {
		this.elementVector = new Vector<Vector<GShapeManager>>();
		
		this.maxSize = 10;
		this.top = 0;
		this.start = 0;			
		this.current = 0;
	}
	
	@SuppressWarnings("unchecked")
	public void initialize(Vector<GShapeManager> shapeVector) {
		this.elementVector.clear();
		this.elementVector.setSize(this.maxSize);
		this.elementVector.set(current, (Vector<GShapeManager>)shapeVector.clone());
		this.current = this.current + 1;
		this.top = this.current;
	}

	private int mod(int value) {
		int diff = value;
		if (diff >= this.maxSize) {
			diff = diff - this.maxSize;
		}
		if (diff < 0) {
			diff = diff + this.maxSize;
		}
		return diff;
	}
	
	@SuppressWarnings("unchecked")
	public void push(Vector<GShapeManager> shapeVector) {
		this.elementVector.set(current, (Vector<GShapeManager>)shapeVector.clone());
		if (mod(current-start) == 0) {
			start = mod(start + 1);
		}
		current = mod(current + 1);			
		top = current;
	}
	
	public Vector<GShapeManager> redo() {
		if (mod(top-current) > 0) {
			@SuppressWarnings("unchecked")
			Vector<GShapeManager> shapeVector = (Vector<GShapeManager>) this.elementVector.get(current).clone();
			current = mod(current+1);
			return shapeVector;
		}
		return null;
		
	}
	
	public Vector<GShapeManager> undo() {
		if ((mod(current-start) > 1) || (current==start)) {
			current = mod(current-1);
			@SuppressWarnings("unchecked")
			Vector<GShapeManager> shapeVector = (Vector<GShapeManager>) this.elementVector.get(mod(current-1)).clone();
			return shapeVector;
		}
		return null;
	}
}


