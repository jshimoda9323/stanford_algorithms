package week5_scc2;

public class Graph {
	public Node[] nodes;
	int numNodes;
	
	public Graph(int numNodes) {
		// Node numbering starts at index 1.
		this.numNodes = numNodes + 1;
		nodes = new Node[this.numNodes];
		for (int i = 1; i < this.numNodes; i++) {
			nodes[i] = new Node();
		}
	}
}
