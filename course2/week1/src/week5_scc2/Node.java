package week5_scc2;

import java.util.ArrayList;

public class Node {
	ArrayList<Integer> outEdges;
	int leaderIndex;
	boolean isExplored;
	
	public Node() {
		outEdges = new ArrayList<Integer>();
		leaderIndex = 0;
		isExplored = false;
	}
	
	public void addEdge(int destinationIndex) {
		boolean found = false;
		// Remove duplicate edges
		for (Integer e : outEdges) {
			if (e == destinationIndex) {
				found = true;
				break;
			}
		}
		if (!found) {
			outEdges.add(destinationIndex);
		}
	}
}
