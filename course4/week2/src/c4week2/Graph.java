package c4week2;

import java.util.Arrays;

public class Graph {
	Node[] nodes;
	private int nodeIdx;
	private float[][] distances;
	
	public Graph(int numNodes) {
		nodes = new Node[numNodes];
		nodeIdx = 0;
		distances = new float[numNodes][numNodes];
		for (float[] ar : distances) {
			Arrays.fill(ar, Float.POSITIVE_INFINITY);
		}
	}
	
	public void addNode(Node n) {
		nodes[nodeIdx] = n;
		nodeIdx++;
	}
	
	public int getNumCities() {
		return(nodeIdx);
	}
	
	// Lazily compute the distance between nodes, cache it for later retrieval.
	public float getDistance(int a, int b) {
		if (distances[a][b] == Float.POSITIVE_INFINITY) {
			if (a == b) {
				distances[a][b] = 0f;
			} else {
				float dx = nodes[a].x - nodes[b].x;
				float dy = nodes[a].y - nodes[b].y;
				distances[a][b] = (float)Math.sqrt((dx*dx)+(dy*dy));	
			}
			distances[b][a] = distances[a][b];
		}
		return(distances[a][b]);
	}
	
}
