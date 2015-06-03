package knn;

import java.util.Comparator;

public class DistanceTupleComparator<R> implements Comparator<DistanceTuple<R>> {

	@Override
	public int compare(DistanceTuple<R> t, DistanceTuple<R> u) {
		if(t.distance <= u.distance){
			return -1;
		}
		return 1;
	}

}
