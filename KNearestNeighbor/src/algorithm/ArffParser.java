package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ArffParser {
	public Dataset parseNumerical(String fileName, String[] exclusions, String className) {
		List<String> exclusionsList = Arrays.asList(exclusions);
		Reader reader = null;
		Scanner scanner = null;
		reader = new InputStreamReader(getClass().getResourceAsStream(fileName));
		scanner = new Scanner(reader);
		
		Dataset<Double> dataset = null;
		ArrayList<Boolean> includeVar = new ArrayList<Boolean>();
		ArrayList<Boolean> isClass = new ArrayList<Boolean>();
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.length() == 0 || line.charAt(0) == '%') { //the line in the arff file is a comment
				continue;
			} else if(line.charAt(0) == '@') { //the line in the arff file is metadata
				String[] tokens = line.split(" ");
				if(tokens[0].equalsIgnoreCase("@relation")) {
					dataset = new Dataset(tokens[1]);
				} else if(tokens[0].equalsIgnoreCase("@attribute")) {
					if(tokens[1].equalsIgnoreCase(className)) {
						isClass.add(true);
						dataset.className = tokens[1];
					} else {
						isClass.add(false);
					}
					if(exclusionsList.contains(tokens[1])) { //check if the variable should be excluded
						includeVar.add(false);
					} else {
						if(!tokens[1].equalsIgnoreCase(className)) {
							fieldNames.add(tokens[1]);
						}
						includeVar.add(true);
					}
				}
			} else { //the line in the arff file is data
				if(line.contains("?")) { //has missing data so we ignore the point
					continue;
				}
				double classValue = 0;
				ArrayList<Double> data = new ArrayList<Double>();
				
				String[] pieces = line.split(",");
				List<String> piecesList = Arrays.asList(pieces);
				for(int i = 0; i < includeVar.size(); i++) {
					if(isClass.get(i)) {
						classValue = Double.parseDouble(piecesList.get(i));
					}
					
					if(includeVar.get(i) && !isClass.get(i)) {
						data.add(Double.parseDouble(piecesList.get(i)));
					}					
				}
				
				Object[] dataObjArray = data.toArray();
				double[] dataArray = new double[dataObjArray.length];
				for(int i = 0; i < dataArray.length; i++) {
					dataArray[i] = ((Double)dataObjArray[i]).doubleValue();
				}
				
				dataset.addDataPoint(dataArray, classValue);
			}			
		}
		
		dataset.fieldNames = fieldNames;
		
		//close all of the file handlers
		try {
			scanner.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataset;
	}
	
	public Dataset parseCategorical(String fileName, String[] exclusions, String className) {
		List<String> exclusionsList = Arrays.asList(exclusions);
		Reader reader = null;
		Scanner scanner = null;
		reader = new InputStreamReader(getClass().getResourceAsStream(fileName));
		scanner = new Scanner(reader);
		
		Dataset<String> dataset = null;
		ArrayList<Boolean> includeVar = new ArrayList<Boolean>();
		ArrayList<Boolean> isClass = new ArrayList<Boolean>();
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.length() == 0 || line.charAt(0) == '%') { //the line in the arff file is a comment
				continue;
			} else if(line.charAt(0) == '@') { //the line in the arff file is metadata
				String[] tokens = line.split(" ");
				if(tokens[0].equalsIgnoreCase("@relation")) {
					dataset = new Dataset<String>(tokens[1]);
				} else if(tokens[0].equalsIgnoreCase("@attribute")) {
					if(tokens[1].equalsIgnoreCase(className)) {
						isClass.add(true);
						dataset.className = tokens[1];
					} else {
						isClass.add(false);
					}
					if(exclusionsList.contains(tokens[1])) { //check if the variable should be excluded
						includeVar.add(false);
					} else {
						if(!tokens[1].equalsIgnoreCase(className)) {
							fieldNames.add(tokens[1]);
						}
						includeVar.add(true);
					}
				}
			} else { //the line in the arff file is data
				if(line.contains("?")) { //has missing data so we ignore the point
					continue;
				}
				String classValue = "";
				ArrayList<Double> data = new ArrayList<Double>();
				
				String[] pieces = line.split(",");
				List<String> piecesList = Arrays.asList(pieces);
				for(int i = 0; i < includeVar.size(); i++) {
					if(isClass.get(i)) {
						classValue = piecesList.get(i);
					}
					
					if(includeVar.get(i) && !isClass.get(i)) {
						data.add(Double.parseDouble(piecesList.get(i)));
					}					
				}
				
				Object[] dataObjArray = data.toArray();
				double[] dataArray = new double[dataObjArray.length];
				for(int i = 0; i < dataArray.length; i++) {
					dataArray[i] = ((Double)dataObjArray[i]).doubleValue();
				}
				
				dataset.addDataPoint(dataArray, classValue);
			}			
		}
		
		dataset.fieldNames = fieldNames;
		
		//close all of the file handlers
		try {
			scanner.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataset;
	}
		
}
