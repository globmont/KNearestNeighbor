package visualization;

import java.util.ArrayList;

import processing.core.PApplet;

public class DropDown{
	String[] values, labels;
	String defaultText;
	float xpos, ypos, width, height, textSize;
	int strokeColor, fillColor, textColor, hoverColor;
	float hmargin = 10, vmargin = 10;
	float totalHeight = 0;
	ArrayList<DropdownListener> listeners;
	PApplet parent;
	AnimationProperties selectionPanel = new AnimationProperties(new String[] {"animationStartHeight", "animationEndHeight"}, new float[] {0, totalHeight}, 
																 new String[] {"animationLength", "currentFrame", "animationStatus"}, new int[] {15, 0, -2}, 
																 null, null);


	float[] labelHeights;
	Option[] labelBlocks;

	String selectedLabel, selectedValue;

	public DropDown(PApplet parent, float xpos, float ypos, float textSize, String defaultText, String defaultValue, String[] values, int fill, int stroke, int text, int hover, ArrayList<DropdownListener> listeners) {
		this(parent, xpos, ypos, textSize, defaultText, defaultValue, values, values, stroke, fill, text, hover, listeners);
	}

	public DropDown(PApplet parent, float xpos, float ypos, float textSize, String defaultText, String defaultValue, String[] labels, String[] values, int fill, int stroke, int text, int hover, ArrayList<DropdownListener> listeners) {
		this.parent = parent;
		this.values = values;
		this.labels = labels;
		this.defaultText = defaultText;
		this.xpos = xpos;
		this.ypos = ypos;
		this.textSize = textSize;
		this.strokeColor = stroke;
		this.fillColor = fill;
		this.textColor = text;
		this.hoverColor = hover;
		if(listeners == null) {
			this.listeners = new ArrayList<DropdownListener>();
		} else {
			this.listeners = listeners;
		}

		selectedLabel = defaultText;
		selectedValue = defaultValue;

		labelHeights = new float[labels.length];
		labelBlocks = new Option[labels.length];

		float maxWidth = parent.textWidth(defaultText);
		float maxHeight = textHeight(defaultText);
		parent.textSize(textSize);
		for(int i = 0; i < labels.length; i++) {
			String s = labels[i];
			labelBlocks[i] = new Option(parent, values[i], labels[i], fillColor);
			float textHeight = textHeight(s);
			labelHeights[i] = textHeight;
			totalHeight += textHeight + 2 * vmargin;
			maxHeight = (maxHeight < textHeight) ? textHeight : maxHeight;
			maxWidth = (maxWidth < parent.textWidth(s)) ? parent.textWidth(s) : maxWidth;
		}

		this.width = maxWidth;
		this.height = maxHeight;
		selectionPanel.set("animationEndHeight", totalHeight);
	}

	private float textHeight(String s) {
		return s.split("\n").length * textSize;
	}

	public void draw() {	
		
		if(this.contains(parent.mouseX, parent.mouseY, true) || this.optionHover()){
			parent.cursor(parent.HAND);
		}
		
		parent.rectMode(parent.CORNER);
		parent.strokeWeight(1);
		parent.fill(fillColor);
		parent.stroke(strokeColor);
		parent.rect(xpos, ypos, width + 3 * hmargin, height + 2 * vmargin);
		parent.rect(xpos + width + 3 * hmargin, ypos, height + 2 * vmargin, height + 2 * vmargin);
		parent.line(xpos + width + 4 * hmargin, ypos + 1.5f * vmargin, xpos + width + 4 * hmargin + (height + 2 * vmargin) / 2.0f - vmargin, ypos + height + .75f * vmargin);
		parent.line(xpos + width + 4 * hmargin + (height + 2 * vmargin) / 2.0f - vmargin, ypos + height + .75f * vmargin, xpos + width + 3 * hmargin + height + 2 * vmargin - hmargin, ypos + 1.5f * vmargin);
		parent.fill(textColor);
		parent.textAlign(parent.LEFT, parent.TOP);
		parent.textSize(textSize);
		parent.text(selectedLabel, xpos + hmargin, ypos + vmargin);

		//animation code
		float panelHeight = 0;
		int animationLength = selectionPanel.getInt("animationLength");
		int currentFrame = selectionPanel.getInt("currentFrame");
		int animationStatus = selectionPanel.getInt("animationStatus");
		float animationStartHeight = selectionPanel.getFloat("animationStartHeight");
		float animationEndHeight = selectionPanel.getFloat("animationEndHeight");
		//println(animationStatus);
		if(Math.abs(animationStatus) != 2) {
			if(animationStatus == 1) {
				//expanding
				panelHeight = expandingEase(currentFrame, animationStartHeight, animationEndHeight, animationLength);
				selectionPanel.set("currentFrame", ++currentFrame);
				if(currentFrame >= animationLength) {
					selectionPanel.set("animationStatus", 2);
				}
			} else {
				//closing
				panelHeight = expandingEase(currentFrame, animationStartHeight, animationEndHeight, animationLength);
				selectionPanel.set("currentFrame", --currentFrame);
				if(currentFrame <= 0) {
					selectionPanel.set("animationStatus", -2);
				}
			}
		} else if(animationStatus == 2) {
			//expanded
			panelHeight = totalHeight;
			selectionPanel.set("currentFrame", animationLength);

		} else {
			//closed
			panelHeight = 0;
			selectionPanel.set("currentFrame", 0);

		}
		
		parent.fill(fillColor);
		parent.rect(xpos, ypos + height + 2 * vmargin, width + 3 * hmargin + height + 2 * vmargin, panelHeight);

		int lastFittingLabel = findLastFittingLabel(panelHeight);
		float runningSum = 0;
		
		for(Option o : labelBlocks) {
			o.displaying = false;
		}
		for(int i = 0; i <= lastFittingLabel; i++) {
			labelBlocks[i].displaying = true;
			labelBlocks[i].draw(xpos, ypos + height + 2 * vmargin + runningSum + vmargin * i * 2, width + 3 * hmargin + height + 2 * vmargin, labelHeights[i] + 2 * vmargin);
			parent.fill(textColor);
			parent.text(labels[i], xpos + hmargin, ypos + height + 2 * vmargin + runningSum + vmargin * i * 2 + vmargin);
			runningSum += labelHeights[i];

		}
		
		
	}

	public String getValue() {
		return selectedValue;
	}

	public String getLabel() {
		return selectedLabel;
	}

	private float expandingEase(float t, float b, float c, float d) {
		t /= d/2;
		if (t < 1) 
			return (float)(-c/2 * (Math.sqrt(1 - t*t) - 1) + b);
		t -= 2;
		return (float)(c/2 * (Math.sqrt(1 - t*t) + 1) + b);
	}

	public void mouseClicked() {
		int animationStatus = selectionPanel.getInt("animationStatus");
		if(this.contains(parent.mouseX, parent.mouseY, true)) {
			//println("Yep");
			//println(animationStatus);
			if(animationStatus == 2) {
				selectionPanel.set("animationStatus", -1);
			} else if(animationStatus == -2) {
				selectionPanel.set("animationStatus", 1);
			}
		} else {
			for(Option o: labelBlocks) {
				if(o.contains(parent.mouseX, parent.mouseY)) {
					selectedLabel = o.getLabel();
					selectedValue = o.getValue();
					itemSelected();
					selectionPanel.set("animationStatus", -1);
				}
			}

			if(animationStatus == 2) {
				selectionPanel.set("animationStatus", -1);
			}
		}
	}

	public boolean contains(float x, float y, boolean includeDisplayArea) {
		float x1 = (includeDisplayArea) ? xpos : xpos + width + 3 * hmargin;
		float y1 = ypos;
		float x2 = xpos + width + 3 * hmargin + height + 2 * vmargin;
		float y2 = ypos + height + 2 * vmargin;

		 return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	}

	private int findLastFittingLabel(float value) {
		float runningTotal = vmargin;
		for(int i = 0; i < labelHeights.length; i++) {
			runningTotal += labelHeights[i] + vmargin;
			if(value < runningTotal) {
				return i - 1;
			}
		}

		return labelHeights.length - 1;
	}

	public boolean optionHover() {
		boolean retVal = false;
		for(Option o: labelBlocks) {
			if(o.contains(parent.mouseX, parent.mouseY) && o.displaying) {
				o.setFill(hoverColor);
				retVal = true;
			} else {
				o.setFill(fillColor);
			}
		}
		
		return retVal;
	}

	public boolean setValue(String value) {
		for(int loop = 0; loop < values.length; loop++) {
			if(value.equals(values[loop])) {
				selectedValue = value;
				selectedLabel = labels[loop];
				return true;
			}
		}

		return false;
	}

	public void itemSelected() {
		for(DropdownListener l: listeners) {
			l.itemSelected(selectedLabel, selectedValue);
		}
	}
}