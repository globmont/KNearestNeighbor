package knn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import processing.core.PApplet;

public class DataPoint<R> {
	public double[] parameters;
	public R result;
	public R prediction;
	public ArrayList<DistanceTuple<R>> distances = new ArrayList<DistanceTuple<R>>();
	public boolean hasCoordinates = false;
	public float x, y;
	public int fillColor;
	public int hoverColor;
	public DataPoint(double[] parameters, R result, Dataset<R> parentSet) {
		this.parameters = parameters;
		this.result = result;
	}
	
	//PRECONDITION: the number of parameters in this point and point p are the same
	//OUTPUT: returns the sum of the differences in each parameter squared: ||p - this||^2
	public double distance(DataPoint<R> p) {
		double sum = 0;
		for(int i = 0; i < parameters.length; i++) {
			sum += Math.pow(this.parameters[i] - p.parameters[i], 2);
		}
		return sum;
	}
	
	public void cacheDistance(DataPoint<R> p) {
		cacheDistance(p, distance(p));
	}
	
	public void cacheDistance(DataPoint<R> p, double distance) {
		distances.add(new DistanceTuple<R>(p, distance));
	}
	
	public void sortDistances() {
		Collections.sort(distances, new DistanceTupleComparator<R>());
	}
	
	public String toString() {
		return "Parameters: " + Arrays.toString(parameters) + "\tResult: " + result;
	}
	
	public void draw(PApplet parent, float x, float y, int fillColor) {
		if(!hasCoordinates) {
			this.x = x;
			this.y = y;
			this.fillColor = fillColor;
		}
		
		parent.noStroke();
		parent.fill(this.fillColor);
		if(this.contains(parent, parent.mouseX, parent.mouseY)) {
			parent.fill(parent.color(255, 255, 0));
			parent.cursor(parent.HAND);
		}
		parent.ellipseMode(parent.RADIUS);
		
		parent.ellipse(this.x, this.y, 5, 5);
		
	}
	
	public boolean contains(PApplet parent, float x, float y) {
		return parent.dist(x, y, this.x, this.y) <= 5;
	}
}
