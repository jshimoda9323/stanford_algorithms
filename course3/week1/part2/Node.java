package Week9p2;

import java.util.*;

public class Node {
	
	public ArrayList<Integer> edgeRefs;
	public boolean visited;
	public int key;
	public int id;

	public Node(int ident) {
		edgeRefs = new ArrayList<Integer>();
		visited = false;
		key = Integer.MAX_VALUE;
		id = ident;
	}
	
	public void addEdgeRef(Integer eRef) {
		edgeRefs.add(eRef);
	}


}
