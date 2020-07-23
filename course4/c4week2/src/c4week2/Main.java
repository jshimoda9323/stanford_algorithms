package c4week2;

import java.io.File;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		Graph cities;
		int numCities;
		
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			numCities = Integer.parseInt(inScanner.nextLine());
			cities = new Graph(numCities);
			while(inScanner.hasNextLine()) {
				String[] line = inScanner.nextLine().split("\\s+");
				float x = Float.parseFloat(line[0]); 
				float y = Float.parseFloat(line[1]);
				cities.addNode(new Node(x,y));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		float distance = tsp_dynamic(cities);
		System.out.println((int)distance);
	}
	
	public static float tsp_dynamic(Graph cities) {
		int numCities = cities.getNumCities();
		Hashtable<Integer,List<Float>> prevResult = new Hashtable<Integer,List<Float>>();
		Hashtable<Integer,List<Float>> currResult = new Hashtable<Integer,List<Float>>();
		float minDist;
		int nextSet = 0;
		
		// Initialize
		prevResult.put(Tspset.bitMasks[0], new ArrayList<Float>());
		for (int i = 0; i < numCities; i++) {
			prevResult.get(Tspset.bitMasks[0]).add(Float.POSITIVE_INFINITY);
		}
		prevResult.get(Tspset.bitMasks[0]).set(0, 0f);
		
		// For every subset size
		for (int m = 2; m <= numCities; m++) {
			
			// Initialize the subset creator
			Tspset set = new Tspset(m,numCities);
			
			// While there is another subset to process
			while(set.validNextState) {
				// Get the next subset state
				nextSet = set.getNextState();
				// Get the actual list of node ids (always includes node 0)
				int[] setList = set.getSet(nextSet);
				
				// For every node id in the subset (except node 0)
				for (int j : setList) {
					if (j != 0) {
						// Compute the minimum distance
						minDist = Float.POSITIVE_INFINITY;
						for (int k : setList) {
							if (j != k) {
								int setMinusj = nextSet & (~Tspset.bitMasks[j]);
								//float testVal = A[setMinusj][k] + cities.getDistance(k, j);
								float testVal;
								if (prevResult.containsKey(setMinusj)) {
									testVal = prevResult.get(setMinusj).get(k) + cities.getDistance(k, j);
								} else {
									testVal = Float.POSITIVE_INFINITY;
								}
								//float testVal = prevResult.get(setMinusj);
								if (minDist > testVal) {
									minDist = testVal;
								}
							}
						}
						if (!currResult.containsKey(nextSet)) {
							currResult.put(nextSet, new ArrayList<Float>());
							for (int i  = 0; i < numCities; i++) {
								currResult.get(nextSet).add(Float.POSITIVE_INFINITY);
							}
						}
						currResult.get(nextSet).set(j, minDist);
					}
				} // for j : setList
			} // while set.validNextState
			prevResult = currResult;
			currResult = new Hashtable<Integer,List<Float>>();
		}
		// Calculate the last remaining distance to node 0
		minDist = Float.POSITIVE_INFINITY;
		for (int j = 1; j < numCities; j++) {
			float currDist = prevResult.get(nextSet).get(j) + cities.getDistance(j, 0);
			if (minDist > currDist) {
				minDist = currDist;
			}
		}
		return(minDist);
	}
	
}
