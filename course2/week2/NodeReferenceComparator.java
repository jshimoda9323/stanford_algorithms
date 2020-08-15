package dsp;

import java.util.Comparator;

public class NodeReferenceComparator implements Comparator<NodeReference> {

	public int compare(NodeReference a, NodeReference b) {
		return(a.score < b.score ? -1 : a.score > b.score ? 1 : 0);
	}

}
