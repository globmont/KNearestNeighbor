package knn;

public class DistanceTuple<R> {
	public final DataPoint<R> point;
	public final double distance;
	
	public DistanceTuple(DataPoint<R> point, double distance) {
		this.point = point;
		this.distance = distance;
	}
}
