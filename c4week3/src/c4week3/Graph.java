package c4week3;

public class Graph {
	
	public double[] node_x;
	public double[] node_y;
	
	private int nodeIdx;
	
	public Graph (int numNodes) {
		node_x = new double[numNodes];
		node_y = new double[numNodes];
		nodeIdx = 0;
	}
	
	public void addNode(double x, double y) {
		node_x[nodeIdx] = x;
		node_y[nodeIdx] = y;
		nodeIdx++;
	}
	
	public int getNumNodes() {
		return(nodeIdx);
	}

}
