package c4week1;

import java.io.File;
import java.util.Scanner;

public class c4week1 {

	public static void main(String[] args) {
		int nodeCount;
		int edgeCount;
		Graph graph;
		long edgePositiveTotal = 0;
		long edgeNegativeTotal = 0;
		
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			String[] line = inScanner.nextLine().split("\\s+");
			nodeCount = Integer.parseInt(line[0]);
			edgeCount = Integer.parseInt(line[1]);
			graph = new Graph(nodeCount,edgeCount);
			while(inScanner.hasNextLine()) {
				line = inScanner.nextLine().split("\\s+");
				int edgeWeight = Integer.parseInt(line[2]);
				if (edgeWeight > 0) {
					edgePositiveTotal += edgeWeight;
				} else {
					edgeNegativeTotal += edgeWeight;
				}
				Edge e = new Edge(Integer.parseInt(line[0]),Integer.parseInt(line[1]),edgeWeight);
				graph.addEdge(e);
			}
		} catch (Exception e) {
				e.printStackTrace();
				return;
		}
		
		// Check for possible edge weights overflow
		if ((edgePositiveTotal > (long)(Integer.MAX_VALUE))||(edgeNegativeTotal < (long)(Integer.MIN_VALUE))) {
			System.out.println("Edge weight over/underflow");
			return;
		}
		
		Path p =  bellmanford.allpairs_bellmanford(graph);
		//Path p = johnson.allpairs_johnson(graph);
		if (p.isInfinite) {
			System.out.println("NULL");
		} else {
			System.out.println(p.cost);
		}
		
	}
}
