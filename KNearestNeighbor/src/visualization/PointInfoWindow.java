package visualization;

import java.util.Arrays;

import algorithm.Dataset;
import algorithm.DistanceTuple;
import processing.core.PApplet;

public class PointInfoWindow {
	
	public boolean active = false;
	public boolean open = false;
	float x, y, maxWidth, maxHeight, width, height;
	int textSize, fillColor;
	DistanceTuple t;
	Dataset d;
	PApplet parent;
	Button b;
	FocusListener f;
	
	public PointInfoWindow(PApplet parent, float x, float y, float width, float height, int textSize, int fillColor) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.maxWidth = width;
		this.maxHeight = height;
		this.width = 0;
		this.height = 0;
		this.textSize = textSize;
		this.fillColor = fillColor;
		
		f = new FocusListener((Visualization) parent);
		
		b = new Button(parent, this.x + 20, this.y - 160, 15, 60, parent.color(56, 119, 128), parent.color(200), parent.color(255), parent.color(106, 169, 178), "Focus", null);
		b.addListener(f);
	}
	
	public void draw() {
		width = (width > maxWidth) ? maxWidth : width;
		height = (height > maxHeight) ? maxHeight : height;
		
		width = (width < 0) ? 0 : width;
		height = (height < 0) ? 0 : height;
		
		open = width >= maxWidth && height >= maxHeight;
		
		parent.rectMode(parent.CENTER);
		parent.fill(fillColor);
		parent.rect(x, y, width, height, 10);
		if(active) {
			width += 20;
			height += 20;
		} else {
			width -= 20;
			height -= 20;
		}
		

		if(active && open) {
			f.obsNumber = d.getDataPointIndex(t.point);
			b.draw();
			
			parent.textAlign(parent.CENTER);
			parent.fill(0);
			parent.text("Point Info (click outside box to return)", this.x, this.y - height / 2.0f + 36);
			parent.stroke(0);
			parent.line(this.x - parent.textWidth("Point Info (click outside box to return)") / 2.0f, this.y - height / 2.0f + 42, this.x + parent.textWidth("Point Info (click outside box to return)") / 2.0f, this.y - height / 2.0f + 42);
			
			for(int i = 0; i < t.point.parameters.length; i++) {
				double[] parameters = t.point.parameters;
				String[] labels = Arrays.copyOf(d.fieldNames.toArray(), d.fieldNames.toArray().length, String[].class);
				float xOffset = (i > 16) ? 115 : 0;
				float yOffset = (i > 16) ? -(height - 80) : 0;
				parent.textAlign(parent.LEFT);
				parent.fill(0);
				parent.text(labels[i] + " = ", this.x - width / 2.0f + 30 + xOffset, this.y - height / 2.0f + 100 + i * 40 + yOffset);
				parent.fill(100);
				parent.text(String.format("%.2f", parameters[i]), this.x - width / 2.0f + 30 + parent.textWidth(labels[i] + " = ") + xOffset, this. y - height / 2.0f + 100 + i * 40 + yOffset);
				
			}
			
			parent.line(this.x, this.y - height / 2.0f + 70, this.x, this.y + height / 2.0f - 45);
			
			parent.fill(0);
			parent.text("Observation number = ", this.x + 20, this.y - 260);
			parent.fill(100);
			parent.text(String.format("%d", d.getDataPointIndex(t.point)), this.x + 20 + parent.textWidth("Observation number = "), this.y - 260);
			
			parent.fill(0);
			parent.text("Distance = ", this.x + 20, this.y - 220);
			parent.fill(100);
			parent.text(String.format("%.2f", t.distance), this.x + 20 + parent.textWidth("Distance = "), this.y - 220);
			
			parent.fill(0);
			parent.text(d.className + " = ", this.x + 20, this.y - 180);
			parent.fill(100);
			parent.text("" + t.point.result, this.x + 20 + parent.textWidth(d.className + " = "), this.y - 180);
		}
	}
	
	public void setTuple(DistanceTuple t) {
		this.t = t;
	}
	
	public void setDataset(Dataset d) {
		this.d = d;
	}
	
	public void mouseClicked() {
		b.mouseClicked();
		if(!this.contains(parent.mouseX, parent.mouseY)){
			this.active = false;
		}
	}
	
	public boolean contains(float xpos, float ypos) {
		return (xpos >= x - width / 2.0) && (xpos <= x + width / 2.0) && (ypos >= y - height / 2.0) && (ypos <= y + height / 2.0);
	}
}
