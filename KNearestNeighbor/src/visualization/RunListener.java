package visualization;

import java.util.ArrayList;
import java.util.HashMap;

import algorithm.DataPoint;
import algorithm.Dataset;
import algorithm.DistanceTuple;
import algorithm.Main;

public class RunListener implements ButtonListener {
	
	Visualization parent;
	double divFactor = 250.0;
	int focusColor, unfocusColor, nearColor, radiusColor;
	Dataset dataset;
	DataPoint currObs;
	ArrayList<DistanceTuple> distances = new ArrayList<DistanceTuple>();
	PointInfoWindow w;
	
	boolean ka, wa, ki, kiris, shouldDraw;
	HashMap<String, Boolean> setFocused = new HashMap<String, Boolean>();
	
	float maxRadius = 0;
	float circleRadius = 0;
	
	public RunListener(Visualization parent) {
		this.parent = parent;
		
		this.focusColor = parent.color(0, 0, 255);
		this.unfocusColor = parent.color(100);
		this.nearColor = parent.color(255, 255, 100);
		this.radiusColor = parent.color(255, 255, 0, 128);
		
		w = new PointInfoWindow(parent, 960, 540, 500, 740, 15, parent.color(255));
	}
	
	@Override
	public void trigger() {
		shouldDraw = true;
		
		int k = Integer.parseInt(parent.k.getValue());
		int n = Integer.parseInt(parent.n.getValue());
		int o = Integer.parseInt(parent.o.getValue());
		
		circleRadius = 0;
		for(String s: parent.d.values){
			setFocused.put(s, false);
		}
		switch(parent.d.getValue()) {
		case "ka":
			kNNAutoVisualizer(k, n, o);
			break;
		case "wa":
			wNNAutoVisualizer(k, n, o);
			break;
		case "ki":
			kNNIonosphereVisualizer(k, n, o);
			break;
		case "kiris":
			kNNIrisVisualizer(k, n, o);
		}
		
		ArrayList<DistanceTuple<Double>> distancesOrig = currObs.distances;
		distances.clear();
		for(int i = 0; i < n; i++) {
			distances.add(distancesOrig.get(i));
			distances.get(i).point.hasCoordinates = false;
		}
		
		double maxDistance = distances.get(distances.size() - 1).distance;
		divFactor = maxDistance / 500.0;
		maxRadius = (float) (distances.get(k - 1).distance / divFactor + 5);
		
		parent.className = dataset.className;
	}
	
	public void draw() {
		if(shouldDraw) {
			parent.ellipseMode(parent.RADIUS);
			parent.noStroke();
			parent.fill(focusColor);
			
			for(DistanceTuple<Double> t : distances) {
				double pixelDistance = t.distance / divFactor;
				DataPoint<Double> point = t.point;
				double angle = Math.random() * 2 * Math.PI;
				double xDiff = Math.cos(angle) * pixelDistance;
				double yDiff = Math.sin(angle) * pixelDistance;
				point.draw(parent, (float)(parent.originX + xDiff), (float)(parent.originY + yDiff), unfocusColor);
				point.hasCoordinates = true;
			}
			circleRadius = (circleRadius >= maxRadius) ? maxRadius : circleRadius;
			parent.ellipseMode(parent.RADIUS);
			parent.fill(radiusColor);
			parent.ellipse(parent.originX, parent.originY, circleRadius, circleRadius);
			circleRadius += 1.5;
			currObs.draw(parent, parent.originX, parent.originY, focusColor);
			
			
			if(setFocused.get("ka")) {
				parent.method = "Mean";
				parent.actualValue = String.format("%.2f", currObs.result);
				parent.predictedValue = String.format("%.2f", currObs.prediction);
				parent.error = String.format("%.2f", dataset.error);
			} else if(setFocused.get("wa")) {
				parent.method = "Weighted average";
				parent.actualValue = String.format("%.2f", currObs.result);
				parent.predictedValue = String.format("%.2f", currObs.prediction);
				parent.error = String.format("%.2f", dataset.error);
			} else if(setFocused.get("ki")) {
				parent.method = "Mode";
				parent.actualValue = (String) currObs.result;
				parent.predictedValue = (String) currObs.prediction;
				parent.error = String.format("%.0f", dataset.error);
			} else if(setFocused.get("kiris")) {
				parent.method = "Mode";
				parent.actualValue = (String) currObs.result;
				parent.predictedValue = (String) currObs.prediction;
				parent.error = String.format("%.0f", dataset.error);
			}
			
			w.draw();
		}
	}
	
	public void mouseClicked() {
		w.mouseClicked();
		for(DistanceTuple t : distances) {
			if((t.point.contains(parent, parent.mouseX, parent.mouseY) || currObs.contains(parent, parent.mouseX, parent.mouseY)) && !w.active) {
				if(currObs.contains(parent, parent.mouseX, parent.mouseY)) {
					w.setTuple(new DistanceTuple(currObs, 0));
				} else {
					w.setTuple(t);
				}
				w.setDataset(dataset);
				w.active = true;
				
			}
		}
	}
	
	public void kNNAutoVisualizer(int k, int n, int o) {
		setFocused.put("ka", true);
		
		this.dataset = Main.runAutosNormal(k);
		this.currObs = dataset.getDataPoint(o);
		
	}
		
	
	public void wNNAutoVisualizer(int k, int n, int o) {
		setFocused.put("wa", true);
		
		this.dataset = Main.runAutosWeighted(k);
		this.currObs = dataset.getDataPoint(o);
	}
	
	public void kNNIonosphereVisualizer(int k, int n, int o) {
		setFocused.put("ki", true);
		
		this.dataset = Main.runIonosphereNormal(k);
		this.currObs = dataset.getDataPoint(o);
	}
	
	public void kNNIrisVisualizer(int k, int n, int o) {
		setFocused.put("kiris", true);
		
		this.dataset = Main.runIris(k);
		this.currObs = dataset.getDataPoint(o);
	}
	
	
	

}
