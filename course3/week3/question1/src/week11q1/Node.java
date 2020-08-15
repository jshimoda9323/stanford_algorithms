package week11q1;

public class Node {
	
	public long weight;
	public Node left;
	public Node right;
	public int depth;

	public Node(long w) {
		left = null;
		right = null;
		weight = w;
	}

}
