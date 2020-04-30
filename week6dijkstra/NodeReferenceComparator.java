package dsp;

import java.util.Comparator;

public class NodeReferenceComparator implements Comparator<NodeReference> {

	public int compare(NodeReference a, NodeReference b) {
		return(a.weight < b.weight ? -1 : a.weight > b.weight ? 1 : 0);
	}

}
