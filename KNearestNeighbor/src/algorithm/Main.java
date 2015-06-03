package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import visualization.Visualization;



public class Main {
	public static String path = "/data/";
	
	public static void main(String[] args) {
		Visualization v = new Visualization();
		v.launch();
	}
	
	public static Dataset<Double> runAutosNormal(int kAut) {
		String[] autosExclusions = new String[] {"make",
				 "fuel-type",
				 "aspiration",
				 "num-of-doors",
				 "body-style",
				 "drive-wheels",
				 "engine-location",
				 "engine-type",
				 "num-of-cylinders",
				 "fuel-system"};



		ArffParser autosParser = new ArffParser();
		Dataset<Double> autos = autosParser.parseNumerical(path + "autos.arff", autosExclusions, "price");
		//start kNN algorithm using leave-one-out cross-validation for autos data set
		
		autos.cacheDistances();
		double error = 0;
		for(int testObs = 0; testObs < autos.size(); testObs++) {
		DataPoint<Double> currObs = autos.getDataPoint(testObs);
		ArrayList<DistanceTuple<Double>> kNN = new ArrayList<DistanceTuple<Double>>();
		for(int i = 0; i < kAut; i++) {
			kNN.add(currObs.distances.get(i));
		}
		
		double sum = 0;
		for(DistanceTuple<Double> t : kNN) { 
			sum += t.point.result;
		}
		
		sum /= kAut;
		error += Math.abs(currObs.result - sum);
		System.out.println(currObs + "\tkNN: " + sum);
			currObs.prediction = sum;
		}
		error /= autos.size();
		System.out.println("Mean Error: " + error + " in " + autos.size() + " instances");
		autos.error = error;
		return autos;
	}

	public static Dataset<Double> runAutosWeighted(int kAut) {
		String[] autosExclusions = new String[] {"make",
				 "fuel-type",
				 "aspiration",
				 "num-of-doors",
				 "body-style",
				 "drive-wheels",
				 "engine-location",
				 "engine-type",
				 "num-of-cylinders",
				 "fuel-system"};



		ArffParser autosParser = new ArffParser();
		Dataset<Double> autos = autosParser.parseNumerical(path + "autos.arff", autosExclusions, "price");
		//start kNN algorithm using leave-one-out cross-validation for autos data set
		
		autos.cacheDistances();
		double error = 0;
		for(int testObs = 0; testObs < autos.size(); testObs++) {
		DataPoint<Double> currObs = autos.getDataPoint(testObs);
		ArrayList<DistanceTuple<Double>> kNN = new ArrayList<DistanceTuple<Double>>();
		for(int i = 0; i < kAut; i++) {
			kNN.add(currObs.distances.get(i));
		}
		
		double totalSum = 0;
		for(DistanceTuple<Double> t : kNN) { 
			totalSum += t.point.result;
		}
		double sum = 0;
		for(DistanceTuple<Double> t : kNN) { 
			sum += t.point.result * (t.point.result / totalSum);
		}
		
		error += Math.abs(currObs.result - sum);
		System.out.println(currObs + "\twNN: " + sum);
		currObs.prediction = sum;
		}
		error /= autos.size();
		System.out.println("Mean Error: " + error + " in " + autos.size() + " instances");
		autos.error = error;
		return autos;
	}
	
	public static Dataset<String> runIonosphereNormal(int kIon) {
		ArffParser ionosphereParser = new ArffParser();
		Dataset<String> ionosphere = ionosphereParser.parseCategorical(path + "ionosphere.arff", new String[] {}, "class");
		//start kNN algorithm using leave-one-out cross-validation for ionosphere data set
		
		ionosphere.cacheDistances();
		int errors = 0;
		for(int testObs = 0; testObs < ionosphere.size(); testObs++) {
			DataPoint<String> currObs = ionosphere.getDataPoint(testObs);
			ArrayList<DistanceTuple<String>> kNN = new ArrayList<DistanceTuple<String>>();
			for(int i = 0; i < kIon; i++) {
				kNN.add(currObs.distances.get(i));
			}
			
			double sum = 0;
			int[] frequencies = new int[2];
			for(DistanceTuple<String> t : kNN) { 
				if(t.point.result.equalsIgnoreCase("b")) {
					frequencies[0]++;
				} else {
					frequencies[1]++;
				}
			}
			String output = ((frequencies[0] > frequencies[1]) ? "b" : "g");
			if(!currObs.result.equals(output)) {
				errors++;
			}
			System.out.println(currObs + "\tkNN: " + output);
			currObs.prediction = output;
		}
		System.out.println("Errors: " + errors + "/" + ionosphere.size());
		ionosphere.error = errors;
		return ionosphere;
	}
		
	public static Dataset<String> runIris(int kIris) {
		ArffParser ionosphereParser = new ArffParser();
		Dataset<String> iris = ionosphereParser.parseCategorical(path + "iris.arff", new String[] {}, "class");
		//start kNN algorithm using leave-one-out cross-validation for ionosphere data set
		
		iris.cacheDistances();
		int errors = 0;
		for(int testObs = 0; testObs < iris.size(); testObs++) {
			DataPoint<String> currObs = iris.getDataPoint(testObs);
			ArrayList<DistanceTuple<String>> kNN = new ArrayList<DistanceTuple<String>>();
			for(int i = 0; i < kIris; i++) {
				kNN.add(currObs.distances.get(i));
			}
			
			double sum = 0;
			Integer[] frequencies = new Integer[3];
			for(int i = 0; i < frequencies.length; i++) {
				frequencies[i] = new Integer(0);
			}
			for(DistanceTuple<String> t : kNN) { 
				if(t.point.result.equalsIgnoreCase("Iris-setosa")) {
					frequencies[0]++;
				} else if(t.point.result.equalsIgnoreCase("Iris-versicolor")){
					frequencies[1]++;
				} else {
					frequencies[2]++;
				}
			}
			
			Integer maxValue = Collections.max(Arrays.asList(frequencies));
			int val = maxValue.intValue();
			String output = "";
			if(val == frequencies[0]) {
				output = "Iris-setosa";
			} else if(val == frequencies[1]) {
				output = "Iris-versicolor";
			} else {
				output = "Iris-virginica";
			}
			if(!currObs.result.equals(output)) {
				errors++;
			}
			System.out.println(currObs + "\tkNN: " + output);
			currObs.prediction = output;
		}
		System.out.println("Errors: " + errors + "/" + iris.size());
		iris.error = errors;
		return iris;
	}

}
