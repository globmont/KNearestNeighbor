package knn;

import java.util.ArrayList;

public class Dataset<R> {
	public ArrayList<String> fieldNames = new ArrayList<String>();
	ArrayList<DataPoint<R>> data = new ArrayList<DataPoint<R>>();
	String name;
	public double error;
	public String className;
	
	public Dataset(String name) {
		this.name = name;
	}
	
	public void addDataPoint(DataPoint<R> p) {
		addDataPoint(p);
	}
	
	public void addDataPoint(double[] parameters, R result) {
		data.add(new DataPoint<R>(parameters, result, this));
	}
	
	public void cacheDistances() {
		for(int i = 0; i < data.size() - 1; i++) {
			for(int j = i + 1; j < data.size(); j++) {
				DataPoint<R> p = data.get(i);
				DataPoint<R> q = data.get(j);
				double distance = p.distance(q);
				p.cacheDistance(q, distance);
				q.cacheDistance(p, distance);
			}
		}
		
		for(DataPoint<R> p : data) {
			p.sortDistances();
		}
	}
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		for(DataPoint<R> p: data) {
			b.append(p.toString() + "\n");
		}
		
		return b.toString();
	}
	
	public int size() {
		return data.size();
	}
	
	public DataPoint<R> getDataPoint(int index) {
		return data.get(index);
	}
	
	public int getDataPointIndex(DataPoint<R> point) {
		return data.indexOf(point);
	}
}
