package week11q1;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node a, Node b) {
		return(a.weight < b.weight ? -1 : a.weight > b.weight ? 1 : 0);
	}

}
