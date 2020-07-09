package c4week3;

import java.io.File;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		int numNodes;
		Graph graph;
		
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			numNodes = Integer.parseInt(inScanner.nextLine());
			graph = new Graph(numNodes);
			while(inScanner.hasNextLine()) {
				String[] line = inScanner.nextLine().split("\\s+");
				float x = Float.parseFloat(line[1]); 
				float y = Float.parseFloat(line[2]);
				graph.addNode(x, y);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		long distance = tsp_heuristic(graph);
		System.out.println(distance);
	}
	
	public static long tsp_heuristic(Graph graph) {
		int numNodes = graph.getNumNodes();
		boolean[] has_visited = new boolean[numNodes];
		int currNodeCount = numNodes-1;
		int currNodeId = 0;
		long totalDistanceInt = 0;
		double totalDistanceDec = 0.0d;
		int minNodeId = 0;
		
		Arrays.fill(has_visited, false);
		has_visited[0] = true;
		
		while(currNodeCount > 0) {			
			// Compute all the distances between the current node and all of the non-visited nodes.
			// Keep track of the smallest one.
			double minDistance = Double.POSITIVE_INFINITY;
			for (int n = 0; n < numNodes; n++) {
				if (!has_visited[n]) {
					double dx = graph.node_x[currNodeId] - graph.node_x[n];
					double dy = graph.node_y[currNodeId] - graph.node_y[n];
					double distance_squared = dx*dx + dy*dy;
					if (distance_squared < minDistance) {
						minDistance = distance_squared;
						minNodeId = n;
					}
				}
			}
			{
				double distance_delta = Math.sqrt(minDistance);
				long integer_delta = (long)distance_delta;
				double decimal_delta = distance_delta - (double)integer_delta;
				totalDistanceInt += integer_delta;
				totalDistanceDec += decimal_delta;
				if (totalDistanceDec > 1.0d) {
					totalDistanceInt += 1;
					totalDistanceDec -= 1.0d;
				}
			}
			currNodeId = minNodeId;
			currNodeCount--;
			has_visited[minNodeId] = true;
		}
		//Verification
		for (int n = 0; n < numNodes; n++) {
			if (!has_visited[n]) {
				return(0);
			}
		}
		// Calculate distance from final node to node 0
		{
			double dx = graph.node_x[minNodeId] - graph.node_x[0];
			double dy = graph.node_y[minNodeId] - graph.node_y[0];
			double distance_delta = Math.sqrt(dx*dx+dy*dy);
			long integer_delta = (long)distance_delta;
			double decimal_delta = distance_delta - (double)integer_delta;
			totalDistanceInt += integer_delta;
			totalDistanceDec += decimal_delta;
			if (totalDistanceDec > 1.0d) {
				totalDistanceInt += 1;
				totalDistanceDec -= 1.0d;
			}
		}
		return(totalDistanceInt);
	}

}
