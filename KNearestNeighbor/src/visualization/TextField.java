package visualization;

import java.util.ArrayList;

import processing.core.PApplet;

public class TextField {
	
	PApplet parent;
	float x, y, width, height, origWidth;
	int fillColor, strokeColor, textColor;
	String text;
	float textSize;
	float hMargin = 10;
	float vMargin = 10;
	float lineSpacing = 1;
	boolean hasFocus = false;
	boolean lineOn = true;
	int blinkSpeed = 35;
	ArrayList<TextFieldListener> listeners = new ArrayList<TextFieldListener>();
	
	public TextField(PApplet parent, float x, float y, float textSize, float width, int fillColor, int strokeColor, int textColor, String startingText) {
		if(startingText == null) {
			this.text = "";
		} else {
			this.text = startingText;
		}
		
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.origWidth = width;
		this.textSize = textSize;
		this.height = textHeight(text) + 2 * vMargin;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.textColor = textColor;
		
		
		
	}
	
	public void draw() {
		if(this.contains(parent.mouseX, parent.mouseY)) {
			parent.cursor(parent.TEXT);
		}
		if(Visualization.frameNumber % blinkSpeed == 0) {
			lineOn = !lineOn;
		}
		
		parent.rectMode(parent.CORNER);
		
		if(parent.textWidth(text) >= origWidth - 2 * hMargin) {
			width = parent.textWidth(text) + 2 * hMargin;
		}
		
		parent.fill(fillColor);
		parent.stroke(strokeColor);		
		parent.rect(x, y, width, height);
		
		parent.textAlign(parent.LEFT, parent.TOP);
		parent.fill(textColor);
		parent.text(text, x + hMargin, y + vMargin);
		
		if(hasFocus && lineOn) {
			float textWidth = parent.textWidth(text);
			float lineX = x + hMargin + textWidth + lineSpacing;
			float lineY1 = y + vMargin;
			float lineY2 = y + height - vMargin + 1;
			parent.stroke(textColor);
			parent.line(lineX, lineY1, lineX, lineY2);
		}
		
	}
	
	public void mouseClicked() {
		if(this.contains(parent.mouseX, parent.mouseY)) {
			hasFocus = true;
		} else {
			hasFocus = false;
		}
	}
		
	public boolean contains(float xpos, float ypos) {
		return (xpos >= x) && (xpos <= x + width) && (ypos >= y) && (ypos <= y + height);
	}
	
	private float textHeight(String s) {
		String[] arr = s.split("\n");
		int length = (arr.length == 0) ? 1 : arr.length;
		return length * textSize;
	}
	
	public void keyTyped() {
		if(hasFocus) {
			if(parent.key == 8) {
				if(text.length() > 0) {
					text = text.substring(0, text.length() - 1);
					triggerListeners();
				}
			} else if(parent.key >= 48 && parent.key <= 57) {
				text += parent.key;
				triggerListeners();
			}
		}
	}
	
	public void addListener(TextFieldListener l) {
		listeners.add(l);
	}
	
	public void triggerListeners() {
		for(TextFieldListener l : listeners) {
			l.trigger(this);
		}
	}
	
	public String getValue() {
		return text;
	}
}
