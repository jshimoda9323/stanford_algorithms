package calcMinCut;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class calcMinCut {

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		
		File inFile = new File(args[0]);
		ArrayList<ArrayList<Integer>> vertexList = new ArrayList<ArrayList<Integer>>();
		
		// Add a dummy element so we can keep the 1-origin indexing of the input dataset
		vertexList.add(new ArrayList<Integer>());
		
		// Consume the input
		try (Scanner inScanner = new Scanner(inFile)) {
            while(inScanner.hasNextLine()) {
            	ArrayList<Integer> inputList = new ArrayList<Integer>();
            	String line = inScanner.nextLine();
            	String[] lineElements = line.split("\\s+");
            	for (int i = 1; i<lineElements.length; i++) {
            		inputList.add(Integer.parseInt(lineElements[i]));
            	}
            	vertexList.add(inputList);
            }
		} catch (Exception e) {
            e.printStackTrace();
            return;
		}
		
		// Run it about n^2*log(n) times
		long runs = (long)(Math.pow((double)(vertexList.size()-1),2.0)*Math.log(vertexList.size()-1));
		
		// Deep copy the array
		ArrayList<ArrayList<Integer>> v = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> m : vertexList) {
			ArrayList<Integer> n = (ArrayList<Integer>)m.clone();
			v.add(n);
		}
		
		// Do an initial run
		System.err.println("Running "+runs+" times...");
		int min = minCut(v);
		
		for (long run=1; run<runs; run++) {
			// Deep copy the input array for every iteration
			v = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> m : vertexList) {
				ArrayList<Integer> n = (ArrayList<Integer>)m.clone();
				v.add(n);
			}
			min = Math.min(minCut(v),min);
			if (run%1000==0) { System.err.println("Done "+run+" min="+min+"..."); }
		}
		System.err.println(min);
	}
	
	// Deep print
	public static void myprint(ArrayList<ArrayList<Integer>> vertexList) {
		for (int i=0; i<vertexList.size(); i++) {
			ArrayList<Integer> l = vertexList.get(i);
			if (l.size()>0) {
				System.err.print(i+": ");
				for (Integer j : l) {
					System.err.print(j+" ");;
				}
				System.err.println("");
			}
		}
	}
	
	public static Integer minCut(ArrayList<ArrayList<Integer>> vertexList) {
		long vertexCount = vertexList.size()-1; // Don't count that dummy element at index 0
		while(vertexCount>2) {
			
			// Get a random edge index
			// Here we get a random number between 0-adjCount.  adjCount is
			// actually the number of edges*2, but good enough and it is
			// still a uniformly random selection of an edge
			int adjCount = 0;
			for (ArrayList<Integer> adjList : vertexList) {
				adjCount += adjList.size();
			}
			int edgeIndex = ThreadLocalRandom.current().nextInt(0,adjCount);
			
			// Get vertices u and v from edgeIndex
			int u=-1;
			int v=-1;
			adjCount = 0;
			for (u=0; u<vertexList.size(); u++) {
				ArrayList<Integer> currentVertex = vertexList.get(u);
				adjCount += currentVertex.size();
				if (adjCount > edgeIndex) {
					v = currentVertex.get(currentVertex.size()-(adjCount-edgeIndex));
					//System.err.println("u="+u+" v="+v);
					break;
				}
			}
			
			// merge v into u
			mergeVertices(vertexList,u,v);
			vertexCount--;
			
		}
		
		// Return the size of just one of the adjacency lists (both lists are the same size)
		for (ArrayList<Integer> a : vertexList) {
			if (a.size()>0) {
				return(a.size());
			}
		}
		return(-1);
	}
	
	public static void mergeVertices(ArrayList<ArrayList<Integer>> vertexList, int u, int v) {
		ArrayList<Integer> vList = vertexList.get(v);
		
		// Remove all self references of v (being careful about modifying list we are iterating)
		ListIterator<Integer> iter = vList.listIterator();
		while(iter.hasNext()) {
			if (iter.next() == v) {
				iter.remove(); 
			}
		}
		
		// Change all external references of v to references of u
		for (Integer vertex : vList) {
			ArrayList<Integer> currentList = vertexList.get(vertex);
			for (int j=0; j<currentList.size(); j++) {
				if (currentList.get(j) == v) { currentList.set(j,u); }
			}
		}
		
		// Append all elements of vertex v onto vertex u
		vertexList.get(u).addAll(vList);
		vertexList.set(v,new ArrayList<Integer>());
		
		// Remove all self references
		ArrayList<Integer> uList = vertexList.get(u);
		iter = uList.listIterator();
		while(iter.hasNext()) {
			Integer z = iter.next();
			if ((z == u)||(z == v)) {
				iter.remove();
			}
		}
	}

}
