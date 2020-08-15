package Week10clust2;

import java.util.Hashtable;

public class Graph {
	
	Node[] nodes;
	Hashtable<Integer,Integer> nodesHash;
	int nodeIdx;
	int bitSize;
	int clusterCount;

	public Graph(int numNodes,int bs) {
		nodes = new Node[numNodes];
		nodesHash = new Hashtable<Integer,Integer>();
		bitSize = bs;
		nodeIdx = 0;
		clusterCount = numNodes;
	}
	
	public int addNode(Node n) {
		nodesHash.put(n.nodeLabel, nodeIdx);
		nodes[nodeIdx] = n;
		nodeIdx++;
		return(nodeIdx-1);
	}
	
	public void printNodes() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println("Node "+i+": "+nodes[i].labelToString(bitSize)+" "+nodes[i].nodeLabel);
		}
	}
	
	public int getLeaderIdx(int nodeIdx) {
		int leaderIdx = nodeIdx;
		Node leader = nodes[nodeIdx];
		while (leader.leaderIdx != -1) {
			leaderIdx = leader.leaderIdx;
			leader = nodes[leaderIdx];
		}
		return(leaderIdx);
	}
	
	public int getLeaderIdxOpt(int nodeIdx) {
		int leaderIdx = nodeIdx;
		Node leader = nodes[nodeIdx];
		int depth = 0;
		while (leader.leaderIdx != -1) {
			leaderIdx = leader.leaderIdx;
			leader = nodes[leaderIdx];
			depth++;
		}
		if (depth > 1) {
			nodes[nodeIdx].leaderIdx = leaderIdx;
		}
		return(leaderIdx);
	}
	
	public int getLeaderDepth(int nodeIdx) {
		int depth = 0;
		Node leader = nodes[nodeIdx];
		while(leader.leaderIdx != -1) {
			leader = nodes[leader.leaderIdx];
			depth++;
		}
		return(depth);
	}
	
	public void flattenChains() {
		for (int nodeIdx = 0; nodeIdx < nodes.length; nodeIdx++) {
			int depth = getLeaderDepth(nodeIdx);
			if (depth > 1) {
				int leader = getLeaderIdx(nodeIdx);
				nodes[nodeIdx].leaderIdx = leader;
			}
		}
	}
	
	public void mergeClusters(int leaderIdxA, int leaderIdxB) {
		nodes[leaderIdxA].leaderIdx = leaderIdxB;
		clusterCount--;
	}
	
	public void computeThreeSpacing() {
		int[] neighbors = nodes[0].computePossibleNeighbors(bitSize);
		
		for (int nodeID = 0; nodeID < nodes.length; nodeID++) {
			Node currentNode = nodes[nodeID];
			neighbors = currentNode.computePossibleNeighbors(bitSize);
			for (int neighborLabel : neighbors) {
				if (nodesHash.containsKey(neighborLabel)) {
					int neighborIdx = nodesHash.get(neighborLabel);
					int leaderIdxOfNode = getLeaderIdxOpt(nodeID);
					int leaderIdxOfNeighbor = getLeaderIdxOpt(neighborIdx);
					if (leaderIdxOfNode != leaderIdxOfNeighbor) {
						mergeClusters(leaderIdxOfNode,leaderIdxOfNeighbor);
					}
				}
			}
		}
	}
	
	public void printAllHammingDistances() {
		for (int i = 0; i < nodes.length; i++) {
			System.out.println("Hams for: "+Node.intToString(nodes[i].nodeLabel, bitSize));
			for (int j = 0; j < nodes.length; j++) {
				if (i != j) {
					System.out.println(Node.intToString(nodes[j].nodeLabel, bitSize)+"  "+Node.getHammingDistance(nodes[i].nodeLabel, nodes[j].nodeLabel));
				}
			}
		}
	}

}
