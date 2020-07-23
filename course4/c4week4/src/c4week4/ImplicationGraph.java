package c4week4;

public class ImplicationGraph {
	public Node[] nodes;
	int numVariables;
	
	public ImplicationGraph(int numVariables) {
		// Node numbering starts at index 1.
		// Number of nodes is 2x number of variables.
		this.numVariables = numVariables;
		int numNodes = (numVariables * 2) + 1;
		nodes = new Node[numNodes];
		for (int i = 1; i < numNodes; i++) {
			nodes[i] = new Node();
		}
	}
	
	public void addClause(int varA, int varB) {
		// Add the first edge (^varA -> varB)
		int notVarAIdx = getNodeIndex(varA*-1);
		int varBIdx = getNodeIndex(varB);
		//nodes[notVarAIdx].outEdges.add(varBIdx);
		nodes[notVarAIdx].addEdge(varBIdx);
		if (varA != varB) {
			// Add the second edge (^varB -> varA)
			int notVarBIdx = getNodeIndex(varB*-1);
			int varAIdx = getNodeIndex(varA);
			// nodes[notVarBIdx].outEdges.add(varAIdx);
			nodes[notVarBIdx].addEdge(varAIdx);
		}
	}
	
	public int getNodeIndex(int varID) {
		if (varID < 0) { return(numVariables + (varID*-1)); }
		return(varID);
	}
}
