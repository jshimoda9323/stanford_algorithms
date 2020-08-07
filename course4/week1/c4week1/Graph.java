package c4week1;

public class Graph {
	public Edge[] edges;
	private int edgeIdx;
	public Node[] nodes;
	
	public Graph(int nodeCt, int edgeCt) {
		edges = new Edge[edgeCt];
		edgeIdx = 0;
		
		// Real Nodes start at index 1.  Virtual node at index 0.
		nodes = new Node[nodeCt+1];
		for (int n = 0; n < nodes.length; n++) {
			nodes[n] = new Node(n);
		}
	}
	
	public int addEdge(Edge e) {
		int r = edgeIdx;
		edges[r] = e;
		nodes[e.dst].addIncomingEdge(e);
		nodes[e.src].addOutgoingEdge(e);
		edgeIdx++;
		return(edgeIdx);
	}
	
	public int getNodeCount() {
		return(nodes.length-1);
	}
}
