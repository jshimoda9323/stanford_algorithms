package Week10clust1;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
	
	public int compare(Edge a, Edge b) {
		return(a.cost < b.cost ? -1 : a.cost > b.cost ? 1 : 0);
	}

}
