package visualization;

import processing.core.*;

public class Visualization extends PApplet {
	private static final long serialVersionUID = 2782564719206803430L;
	
	public void launch() {
		PApplet.main(new String[] {"visualization.Visualization"});
	}
	
	
	public DropDown d;
	public TextField k;
	public TextField n;
	public TextField o;
	public Button b;
	public Button ib;
	public InstructionsWindow i;
	static int frameNumber = 0;
	
	String className = "value";
	
	String kFieldDefaultText = "3";	
	String nFieldDefaultText = "5";
	String oFieldDefaultText = "1";
	String dropdownDefaultLabel = "kNN: Autos";
	String dropdownDefaultValue = "ka";
	String[] dropdownLabels = new String[] {"kNN: Autos", "wNN: Autos", "kNN: Ionosphere", "kNN: Iris"};
	String[] dropdownValues = new String[] {"ka", "wa", "ki", "kiris"};
	
	String method = "N/A";
	String actualValue = "N/A";
	String predictedValue = "N/A";
	String error = "N/A";
	
	float originX = 960;
	float originY = 540;
	
	RunListener r = new RunListener(this);
	InstructionsListener il = new InstructionsListener(this);
	
	public void setup() {
		size(1920, 1080);
		d = new DropDown(this, 190, 15, 15, dropdownDefaultLabel, dropdownDefaultValue, dropdownLabels, dropdownValues, color(56, 119, 128), color(200), color(255), color(106, 169, 178), null);
		k = new TextField(this, 480, 15, 15, 50, color(56, 119, 128), color(200), color(255), kFieldDefaultText);
		n = new TextField(this, 835, 15, 15, 50, color(56, 119, 128), color(200), color(255), nFieldDefaultText);
		o = new TextField(this, 1100, 15, 15, 50, color(56, 119, 128), color(200), color(255), oFieldDefaultText);
		b = new Button(this, 1200, 15, 15, 50, color(56, 119, 128), color(200), color(255), color(106, 169, 178), "Run", null);
		ib = new Button(this, 1300, 15, 15, 107, color(56, 119, 128), color(200), color(255), color(106, 169, 178), "Instructions", null);
		i = new InstructionsWindow(this, 960, 540, 800, 600, 15, color(255));
		
		i.active = true;
		
		b.addListener(r);
		ib.addListener(il);
	}
	
	public void draw() {
		background(215);
		
		if(!d.contains(mouseX, mouseY, true) && !k.contains(mouseX, mouseY) && !b.contains(mouseX, mouseY)) {
			cursor(ARROW);
		}
		
		fill(0);
		textAlign(LEFT, TOP);
		text("Algorithm and dataset: ", 10, 24);
		text("k = ", 450, 24);
		text("Number of neighbors to display = ", 580, 24);
		text("Observation number = ", 925, 24);
		
		
		//draw axes
		stroke(0);
		strokeWeight(1);
		line(960, 100, 960, 920);
		line(100, 540, 1820, 540);
		
		drawInfoPanel();
		
		
		d.draw();
		k.draw();
		n.draw();
		o.draw();
		b.draw();
		ib.draw();
		r.draw();
		i.draw();
		frameNumber++;
	}
	
	public void mouseClicked() {
		d.mouseClicked();
		k.mouseClicked();
		n.mouseClicked();
		o.mouseClicked();
		b.mouseClicked();
		ib.mouseClicked();
		r.mouseClicked();
		i.mouseClicked();
	}
	
	public void keyTyped() {
		k.keyTyped();
		n.keyTyped();
		o.keyTyped();
	}
	
	public void drawInfoPanel() {
		fill(color(56, 119, 128));
		noStroke();
		rectMode(CORNER);
		rect(1600, 10, 255, 180);
		
		fill(255);
		textAlign(CENTER);
		text("Info", 1727, 35);
		stroke(255);
		line(1727 - textWidth("Info") / 2, 38, 1727 + textWidth("Info") / 2, 38);
		
		textAlign(LEFT);
		
		fill(255);
		text("Method: ", 1610, 75);
		fill(200);
		text(method, 1610 + textWidth("Method: "), 75);
		
		fill(255);
		text("Actual " + className + ": ", 1610, 100);
		fill(200);
		text(actualValue, 1610 + textWidth("Actual " + className + ": "), 100);
		
		fill(255);
		text("Predicted " + className + ": ", 1610, 125);
		fill(200);
		text(predictedValue, 1610 + textWidth("Predicted " + className + ": "), 125);
		
		fill(255);
		text("Overall dataset error: ", 1610, 150);
		fill(200);
		text(error, 1610 + textWidth("Overall dataset error: "), 150);
		
		fill(255);
		text("Overall dataset size: ", 1610, 175);
		fill(200);
		String sizeValue = (r.dataset == null) ? "N/A" : String.format("%d", r.dataset.size());
		text(sizeValue, 1610 + textWidth("Overall dataset size: "), 175);
	}
	
}
