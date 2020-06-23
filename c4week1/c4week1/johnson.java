package c4week1;

public class johnson {

	static public Path allpairs_johnson(Graph graph) {
		
		// Copy the input graph
		Graph bellmangraph = new Graph(graph.getNodeCount(),graph.edges.length+graph.getNodeCount());
		for (Edge e : graph.edges) {
			bellmangraph.addEdge(e);
		}
		// Add an edge from virtual node zero to every real node in the new graph
		for (int n = 1; n < graph.getNodeCount()+1; n++) {
			bellmangraph.addEdge(new Edge(0,n,0));
		}
		
		// Compute vertex weights using the bellmanford algorithm with virtual source node 0
		Path[] vertexWeights = new Path[bellmangraph.getNodeCount()+1];
		Path shortestPath = bellmanford.bellmanford_shortest_path(0, bellmangraph, vertexWeights);
		if (shortestPath.isInfinite) {
			return(shortestPath);
		}
		
		// Compute new edge weights in preparation for dijkstra's algorithm
		// Ignore all edges whose source is the virtual node zero.
		for (Edge e : bellmangraph.edges) {
			if (e.src == 0) { continue; }
			e.weight = e.weight + vertexWeights[e.src].cost - vertexWeights[e.dst].cost;
			if (e.weight < 0) {
				System.err.println("Error: computed edge weight for dijkstra's algorithm is negative.");
				return(null);
			}
		}
		
		// Run dijkstra's algorithm over all vertices (with new positive edge weights!)
		int[] shortestPaths = new int[bellmangraph.getNodeCount()+1];
		Path shortestShortestPath = new Path(false,Integer.MAX_VALUE);
		for (int n = 1; n < bellmangraph.getNodeCount()+1; n++) {
			shortestPath = dijkstra.dijkstra_shortest_path(n, bellmangraph, shortestPaths);
			
			// Compute the distances for the original graph, and save the shortest distance
			shortestPath.cost = Integer.MAX_VALUE;
			for (int v = 1; v < shortestPaths.length; v++) {
				shortestPaths[v] = shortestPaths[v] - vertexWeights[n].cost + vertexWeights[v].cost;
				if (shortestPath.cost > shortestPaths[v]) {
					shortestPath.cost = shortestPaths[v];
				}
			}
			if (shortestShortestPath.cost > shortestPath.cost) {
				shortestShortestPath.cost = shortestPath.cost;
			}
		}
		return(shortestShortestPath);
	}
}
