package visualization;

import java.util.Arrays;

import knn.Dataset;
import knn.DistanceTuple;
import processing.core.PApplet;

public class InstructionsWindow {
	
	public boolean active = false;
	public boolean open = false;
	float x, y, maxWidth, maxHeight, width, height;
	int textSize, fillColor;
	PApplet parent;
	
	public InstructionsWindow(PApplet parent, float x, float y, float width, float height, int textSize, int fillColor) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.maxWidth = width;
		this.maxHeight = height;
		this.width = 0;
		this.height = 0;
		this.textSize = textSize;
		this.fillColor = fillColor;
		
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
			
			parent.textAlign(parent.CENTER);
			parent.fill(0);
			parent.text("Instructions (click outside box to return)", this.x, this.y - height / 2.0f + 45);
			parent.stroke(0);
			parent.line(this.x - parent.textWidth("Instructions (click outside box to return)") / 2.0f, this.y - height / 2.0f + 50, this.x + parent.textWidth("Instructions (click outside box to return)") / 2.0f, this.y - height / 2.0f + 50);
			
			parent.textAlign(parent.LEFT);
			parent.fill(100);
			String instructions = "This window allows you to visualize the kNN and wNN algorithms. "
					+ "To simply see output via the console,\nrun this jar file from the console and the output should be printed via standard output when you click\nthe run button in the visualization.\n\n"
					+ "Use the dropdown, fill in the text fields to select the options for the visualization, and click run.\n"
					+ "Then, click any point displayed on the screen to see more information about that data point.\nTo focus that datapoint, just click the focus button.\n\n"
					+ "Note that the points' placements are randomized each time you click run, though the distance from\nthe origin remains the same. "
					+ "Thus, if you click run twice with the same observation selected, you may\nget 2 different looking visualizations, though the data displayed is the same.\n\n"
					+ "To see these instructions again, click the instructions button in the top right. Click anywhere outside\nthis box to exit.\n\n"
					+ "Made by Nikhil Prasad.";
			parent.text(instructions, this.x - this.width / 2.0f + 30, this.y - 225);
			
		}
	}
	
	public void mouseClicked() {
		if(!this.contains(parent.mouseX, parent.mouseY) && !((Visualization)parent).ib.contains(parent.mouseX, parent.mouseY)){
			this.active = false;
		}
	}
	
	public boolean contains(float xpos, float ypos) {
		return (xpos >= x - width / 2.0) && (xpos <= x + width / 2.0) && (ypos >= y - height / 2.0) && (ypos <= y + height / 2.0);
	}
}
