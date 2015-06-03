package visualization;

import java.util.ArrayList;

import processing.core.PApplet;

public class Button {
	PApplet parent;
	String text;
	float x, y, width, height;
	float hMargin = 10;
	float vMargin = 10;
	int textSize;
	int fillColor, strokeColor, textColor, hoverColor;
	ArrayList<ButtonListener> listeners;
	public Button(PApplet parent, float x, float y, int textSize, float width, int fillColor, int strokeColor, int textColor, int hoverColor, String text, ArrayList<ButtonListener> listeners) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.textSize = textSize;
		this.width = width;
		this.height = textHeight(text) + 2 * vMargin; 
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.textColor = textColor;
		this.hoverColor = hoverColor;
		this.text = text;
		
		if(listeners != null) {
			this.listeners = listeners;
		} else {
			this.listeners = new ArrayList<ButtonListener>();
		}
	}
	
	public void draw() {
		if(this.contains(parent.mouseX, parent.mouseY)) {
			parent.cursor(parent.HAND);
			parent.fill(hoverColor);
		} else {
			parent.fill(fillColor);
		}
		
		parent.stroke(strokeColor);
		parent.rectMode(parent.CORNER);
		parent.rect(x, y, width, height);
		
		parent.textAlign(parent.CENTER, parent.CENTER);
		parent.fill(textColor);
		parent.text(text, x + hMargin + (parent.textWidth(text) / 2.0f), y + vMargin + (textHeight(text) / 2.0f));
	}
	
	public void mouseClicked() {
		if(this.contains(parent.mouseX, parent.mouseY)) {
			for(ButtonListener b : listeners) {
				b.trigger();
			}
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
	
	public void addListener(ButtonListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ButtonListener listener) {
		listeners.remove(listener);
	}
}
