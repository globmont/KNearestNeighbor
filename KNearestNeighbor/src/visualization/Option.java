package visualization;

import processing.core.PApplet;

public class Option {
	String value, label;
	float xpos, ypos, width, height;
	int fillColor;
	PApplet parent;
	boolean displaying = false;
	
	public Option(PApplet parent, String value, String label, int fillColor) {
		this.value = value;
		this.label = label;
		this.fillColor = fillColor;
		this.parent = parent;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public void draw(float xpos, float ypos, float width, float height) {
		parent.rectMode(parent.CORNER);
		parent.fill(fillColor);
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
		parent.rect(xpos, ypos, width, height);
	}

	public boolean contains(float x, float y) {
		return x >= xpos && x <= xpos + width && y >= ypos && y <= ypos + height && displaying;
	}

	public void setFill(int c) {
		fillColor = c;
	}
}