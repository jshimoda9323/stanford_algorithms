package Week10clust1;

import java.util.Arrays;

public class Graph {
	
	private Node[] nodes;
	private Edge[] edges;
	private int edgeIdx;
	private int clusterCount;

	public Graph(int numNodes, int numEdges) {
		nodes = new Node[numNodes+1];
		for (int i = 1; i<nodes.length; i++) {
			nodes[i] = new Node();
		}
		edges = new Edge[numEdges];
		for (int i = 0; i < edges.length; i++) {
			edges[i] = new Edge(0,0,0);
		}
		edgeIdx = 0;
		clusterCount = numNodes;
	}
	
	public void setNextEdge(int src, int dst, int cost) {
		edges[edgeIdx].src = src;
		edges[edgeIdx].dst = dst;
		edges[edgeIdx].cost = cost;
		edgeIdx++;
	}
	
	public int computeKCluster(int k) {
		Arrays.sort(edges,new EdgeComparator());
		int edgeId = 0;
		for (edgeId = 0; clusterCount >= k; edgeId++) {
			if (getLeader(edges[edgeId].src) == getLeader(edges[edgeId].dst)) {
				continue;
			}
			mergeClusters(edges[edgeId].src,edges[edgeId].dst);
		}
		return(edges[edgeId].cost);
	}
	
	public int getLeader(int nodeID) {
		Node n = nodes[nodeID];
		int leader = nodeID;
		while(n.leader != 0) {
			leader = n.leader;
			n = nodes[n.leader];
		}
		return(leader);
	}
	
	public void mergeClusters(int nodeA, int nodeB) {
		Node leaderOfA = nodes[nodeA];
		int IDofLeaderA = nodeA;
		Node leaderOfB = nodes[nodeB];
		int IDofLeaderB = nodeB;
		
		while(leaderOfA.leader != 0) {
			IDofLeaderA = leaderOfA.leader;
			leaderOfA = nodes[leaderOfA.leader];
		}
		while(leaderOfB.leader != 0) {
			IDofLeaderB = leaderOfB.leader;
			leaderOfB = nodes[leaderOfB.leader];
		}
		if (leaderOfA.clusterSize < leaderOfB.clusterSize) {
			leaderOfA.leader = IDofLeaderB;
			leaderOfB.clusterSize += leaderOfA.clusterSize;
			leaderOfA.clusterSize = 0;
		} else {
			leaderOfB.leader = IDofLeaderA;
			leaderOfA.clusterSize += leaderOfB.clusterSize;
			leaderOfB.clusterSize = 0;
		}
		clusterCount--;
	}
	
	public void printEdges() {
		for (int i = 0; i < edges.length; i++) {
			System.out.println("Edge "+i+" = ("+edges[i].src+","+edges[i].dst+","+edges[i].cost+")");
		}
	}

}
