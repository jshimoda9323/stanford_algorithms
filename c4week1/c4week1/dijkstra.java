package c4week1;

import java.util.*;

public class dijkstra {
	
	static public Path dijkstra_shortest_path(int sourceID, Graph graph, int[] shortestPaths) {
		PriorityQueue<Edge> frontier = new PriorityQueue<Edge>(new EdgeComparator());
		
		boolean[] processed = new boolean[graph.getNodeCount()+1];
		int[] distance = new int[graph.getNodeCount()+1];
		
		// Initialize the arrays
		Arrays.fill(processed, false);
		processed[sourceID] = true;
		distance[sourceID] = 0;
		
		// Populate the min heap
		for (Edge e : graph.nodes[sourceID].outEdges) {
			frontier.add(e);
		}
		
		// Iterate through all edges
		while(!frontier.isEmpty()) {
			Edge nextEdge = frontier.poll();
			
			// Skip if we have already processed the next node
			if (processed[nextEdge.dst]) { continue; }
			
			distance[nextEdge.dst] = distance[nextEdge.src] + nextEdge.weight; 
			processed[nextEdge.dst] = true;
			
			for (Edge e : graph.nodes[nextEdge.dst].outEdges) {
				if (!processed[e.dst]) {
					frontier.add(e);
				}
			}
		}
		
		// Compute the shortest paths, and the shortest of shortest paths
		Path shortestPath = new Path(false,Integer.MAX_VALUE);
		for (int n = 1; n < distance.length; n++) {
			shortestPath.cost = Math.min(shortestPath.cost, distance[n]);
			if (shortestPaths != null) {
				shortestPaths[n] = distance[n];
			}
		}
		return(shortestPath);
	}

}
