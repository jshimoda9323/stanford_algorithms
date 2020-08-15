package Week10clust1;

import java.util.*;

public class Graph {
	
	private Node[] nodes;
	private ArrayList<Edge> edges;
	private int clusterCount;

	public Graph(int numNodes) {
		nodes = new Node[numNodes+1];
		for (int i = 1; i<nodes.length; i++) {
			nodes[i] = new Node();
		}
		edges = new ArrayList<Edge>();
		clusterCount = numNodes;
	}
	
	public void setNextEdge(int src, int dst, int cost) {
		edges.add(new Edge(src,dst,cost));
	}
	
	public int computeKCluster(int k) {
		edges.sort(new EdgeComparator());
		int edgeId = 0;
		for (edgeId = 0; clusterCount >= k; edgeId++) {
			mergeClusters(edges.get(edgeId).src,edges.get(edgeId).dst);
		}
		edgeId--;
		return(edges.get(edgeId).cost);
	}
	
	public int getLeader(int nodeID) {
		Node n = nodes[nodeID];
		int leader = nodeID;
		while(n.leader != 0) {
			leader = n.leader;
			n = nodes[n.leader];
		}
		if ((leader != nodeID)&&(nodes[nodeID].leader != leader)) { nodes[nodeID].leader = leader; }
		return(leader);
	}
	
	public void mergeClusters(int inIdNodeA, int inIdNodeB) {
		int idA = getLeader(inIdNodeA);
		int idB = getLeader(inIdNodeB);
		if (idA == idB) { return; }
		Node nodeA = nodes[idA];
		Node nodeB = nodes[idB];
		if (nodeA.leader != 0) { System.err.println("A is not a leader"); }
		if (nodeB.leader != 0) { System.err.println("B is not a leader"); }
		if (nodeA.clusterSize < nodeB.clusterSize) {
			nodeA.leader = idB;
			nodeB.clusterSize += nodeA.clusterSize;
			nodeA.clusterSize = 0;
		} else {
			nodeB.leader = idA;
			nodeA.clusterSize += nodeB.clusterSize;
			nodeB.clusterSize = 0;
		}
		clusterCount--;
	}
	
	public void printEdges() {
		for (int i = 0; i < edges.size(); i++) {
			System.out.println("Edge "+i+" = ("+edges.get(i).src+","+edges.get(i).dst+","+edges.get(i).cost+")");
		}
	}

}
