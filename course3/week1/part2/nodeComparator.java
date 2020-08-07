package Week9p2;

import java.util.Comparator;

public class nodeComparator implements Comparator<Node> {

	public int compare(Node a, Node b) {
		return(a.key < b.key ? -1 : a.key > b.key ? 1 : 0);
	}

}
