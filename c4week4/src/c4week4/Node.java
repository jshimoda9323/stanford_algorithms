package c4week4;

import java.util.*;

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
