package Week9p2;

import java.util.PriorityQueue;

public class Graph {
	
	Node[] nodes;
	Edge[] edges;
	
	int edgeIdx;

	public Graph(int numNodes, int numEdges) {
		nodes = new Node[numNodes+1];
		edges = new Edge[numEdges];
		edgeIdx = 0;
	}
	
	public void addEdge(Edge e) {
		if (nodes[e.source] == null) {
			nodes[e.source] = new Node(e.source); 
		}
		nodes[e.source].addEdgeRef(edgeIdx);
		if (nodes[e.dest] == null) {
			nodes[e.dest] = new Node(e.dest); 
		}
		nodes[e.dest].addEdgeRef(edgeIdx); 
		edges[edgeIdx] = e;
		edgeIdx++;
	}
	
	public long computeMST() {
		PriorityQueue<Node> nodeHeap = new PriorityQueue<Node>(new nodeComparator());
		long totalCost;
		
		totalCost = 0;
		nodes[1].visited = true;
		for (int edgeRef : nodes[1].edgeRefs) {
			Edge e = edges[edgeRef];
			if (e.source != 1) {
				nodeHeap.remove(nodes[e.source]);
				recomputeKey(e.source);
				nodeHeap.add(nodes[e.source]);
			}
			if (e.dest != 1) {
				nodeHeap.remove(nodes[e.dest]);
				recomputeKey(e.dest);
				nodeHeap.add(nodes[e.dest]);
			}
		}
		
		while (!nodeHeap.isEmpty()) {
			Node nodeRemoved = nodeHeap.poll();
			nodeRemoved.visited = true;
			totalCost += nodeRemoved.key;
			for (int edgeRef : nodeRemoved.edgeRefs) {
				Edge e = edges[edgeRef];
				if ((e.source != nodeRemoved.id)&&(nodes[e.source].visited == false)) {
					nodeHeap.remove(nodes[e.source]);
					recomputeKey(e.source);
					nodeHeap.add(nodes[e.source]);
				}
				if ((e.dest != nodeRemoved.id)&&(nodes[e.dest].visited == false)) {
					nodeHeap.remove(nodes[e.dest]);
					recomputeKey(e.dest);
					nodeHeap.add(nodes[e.dest]);
				}
			}
			
		}
		
		return(totalCost);
	}
	
	public void recomputeKey(int nodeRef) {
		Node n = nodes[nodeRef];
		n.key = Integer.MAX_VALUE;
		for (int edgeRef : n.edgeRefs) {
			Edge e = edges[edgeRef];
			if (((nodes[e.source].visited)&&(!nodes[e.dest].visited)) ||
			    ((nodes[e.dest].visited)&&(!nodes[e.source].visited))) {
				n.key = Math.min(n.key, e.cost);
			}
		}
	}

}
