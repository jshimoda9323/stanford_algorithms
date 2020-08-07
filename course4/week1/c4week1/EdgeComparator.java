package c4week1;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
	
	public int compare(Edge a, Edge b) {
		return(a.weight > b.weight ? 1 : a.weight < b.weight ? -1 : 0);
	}

}
