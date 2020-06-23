package c4week1;

public class bellmanford {
	
	// Returns the shortest of the shortest paths.  Toggles the isInfinite flag if negative cycle detected.
	static public Path allpairs_bellmanford(Graph graph) {
		//Path[] shortestPaths = new Path[graph.getNodeCount()+1];
		
		// Compute the shortest paths for node 1 and quit early if negative cycle detected
		Path shortestPath = bellmanford_shortest_path(1, graph, null);
		if (shortestPath.isInfinite) {
			return(shortestPath);
		}
		
		// Compute remaining shortest paths
		for (int v = 2; v <= graph.getNodeCount(); v++) {
			Path newShortestPath = bellmanford_shortest_path(v, graph, null);
			if (shortestPath.cost > newShortestPath.cost) {
				shortestPath = newShortestPath;
			}
		}
		return(shortestPath);
	}

	// Returns the shortest path from sourceID to any other node.
	// shortestPaths is returned with the shortest path lengths to each node if provided with a
	//   Path array with length graph.getNodeCount()+1.
	// If the sourceID is zero, vertex indexing starts at zero.  Otherwise it starts at 1.
	static public Path bellmanford_shortest_path(int sourceID, Graph graph, Path[] shortestPaths) {
		Path[][] A = new Path[2][graph.getNodeCount()+1];
		int oldIdx;
		int newIdx;
		boolean has_changed = false;
		Path infinitePath = new Path(true,0);
		int i;
		int vStart;
		Path shortestPath;
		
		// Start vertex indexing at 0 when sourceID is virtual node zero.
		if (sourceID == 0) {
			vStart = 0;
		} else {
			vStart = 1;
		}
		
		// Initialize path lengths
		for (int v = 0; v < A[0].length; v++) {
			A[0][v] = infinitePath;
			A[1][v] = infinitePath;
		}
		A[0][sourceID] = new Path(false, 0);
		
		// Compute path lengths
		for (i = 1; i < graph.getNodeCount()+1; i++) {
			oldIdx = (i+1)%2;
			newIdx = i%2;
			if (bellmanford_update_distance(A,oldIdx,newIdx,vStart,graph) == false) {
				break;
			}
		}
		
		// Return negative edge cycles detected if the last iteration has changes
		if (has_changed == true) {
			return(new Path(true,0));
		}
		// If we processed all vertices, run it one more time on i-1, i to determine if there are negative edge cycles
		if (i >= graph.getNodeCount()+1) {
			if (bellmanford_update_distance(A,(i+1)%2,i%2,vStart,graph)) {
				return(new Path(true,0));
			}
			i = A.length-1;
		}
		
		// Compute shortest paths and shortest of shortest paths.
		shortestPath = infinitePath;
		for (int j = vStart; j < A[i%2].length; j++) {
			if  (A[i%2][j].isInfinite == false) {
				if (shortestPath.isInfinite == false) {
					if (shortestPath.cost > A[i%2][j].cost) {
						shortestPath = A[i%2][j];
					}
				} else {
					shortestPath = A[i%2][j];
				}
			}
			if (shortestPaths != null) {
				shortestPaths[j] = A[i%2][j];
			}
		}
		return(shortestPath);
	}
	
	static public boolean bellmanford_update_distance(Path[][] A, int oldIdx, int newIdx, int vStart, Graph graph) {
		boolean has_changed = false;
		for (int v = vStart; v < A[0].length; v++) {
			Path case2 = new Path(true,0);
			for (Edge e : graph.nodes[v].inEdges) {
				if (A[oldIdx][e.src].isInfinite == false) {
					if (case2.isInfinite == false) {
						case2.cost = Math.min(A[oldIdx][e.src].cost + e.weight, case2.cost);
					} else {
						case2.cost = A[oldIdx][e.src].cost + e.weight;
						case2.isInfinite = false;
					}
				}
			}
			Path oldPath = A[oldIdx][v];
			if (oldPath.isInfinite == false) {
				if (case2.isInfinite == false) {
					if (oldPath.cost > case2.cost) {
						A[newIdx][v] = case2;
						has_changed = true;
					} else {
						A[newIdx][v] = oldPath;
					}
				} else {
					A[newIdx][v] = oldPath;
				}
			} else {
				if (case2.isInfinite == false) {
					A[newIdx][v] = case2;
					has_changed = true;
				} else {
					A[newIdx][v] = oldPath;
				}
			}
		}
		return(has_changed);
	}

}