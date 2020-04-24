package week5_scc;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class scc {
	
	static int sourceNodeIndex;
	static int currentTime;
	static final int inputSize = 875714+1;
	//static final int inputSize = 9+1;

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		
		GraphNode[] graphForward = new GraphNode[inputSize];
		GraphNode[] graphReverse = new GraphNode[inputSize];
		
		for (int i = 1; i < inputSize; i++) {
			graphForward[i] = new GraphNode();
			graphReverse[i] = new GraphNode();
		}
		
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
          while(inScanner.hasNextLine()) {
        	String line = inScanner.nextLine();
        	String[] lineElements = line.split("\\s+");
        	int inputNodeNum = Integer.parseInt(lineElements[0]);
        	int inputEdge = Integer.parseInt(lineElements[1]);
        	GraphNode currentNode = graphForward[inputNodeNum];
        	GraphNode currentNodeRev = graphReverse[inputEdge];
        	currentNode.addEdge(inputEdge);
        	currentNodeRev.addEdge(inputNodeNum);
        	graphForward[inputNodeNum] = currentNode;
        	graphReverse[inputEdge] = currentNodeRev;
          }
		} catch (Exception e) {
		        e.printStackTrace();
		        return;
		}
		
		// DFS_Loop on reversed graph first
		DFS_Loop(graphReverse);
		
		// Reassign nodes according to finishing times
		GraphNode[] newGraph = new GraphNode[graphForward.length];
		for (int i=1; i<newGraph.length; i++) {
			int currFTime = graphReverse[i].getFinishingTime();
			newGraph[currFTime] = graphForward[i];
			ArrayList<Integer> newEdgeList = new ArrayList<Integer>();
			for (Integer j : newGraph[currFTime].edgeList) {
				int newEdge = graphReverse[j].getFinishingTime();
				newEdgeList.add(newEdge);
			}
			newGraph[currFTime].edgeList = newEdgeList;
		}
		graphForward = newGraph;
		
		// Final DFS_Loop to find leaders
		ArrayList<Integer> topSCC = DFS_Loop(graphForward);
		System.err.println("top SCCs:");
		for (Integer i : topSCC) {
			System.err.print(i+",");
		}
		
	}
	
	public static ArrayList<Integer> DFS_Loop(GraphNode[] inputGraph) {
		ArrayList<Integer> top5 = new ArrayList<Integer>();
		top5.add(0);top5.add(0);top5.add(0);top5.add(0);top5.add(0);
		currentTime = 0;
		for (int i = inputGraph.length-1; i>0; i--) {
			GraphNode node = inputGraph[i];
			if ((node != null)&&(!node.isExplored())) {
				sourceNodeIndex = i;
				int nodesExplored = DFS(inputGraph,i);
				for (int j=0; j<top5.size(); j++) {
					if (nodesExplored > top5.get(j)) {
						top5.add(j,nodesExplored);
						break;
					}
				}
				if (top5.size() > 5) { top5.remove(5); }
			}
		}
		return(top5);
	}
	
	public static int DFS(GraphNode[] inputGraph,int nodeID) {
		GraphNode node = inputGraph[nodeID];

		int nodesExplored = 0;
		Stack<Integer> nodeStack = new Stack<Integer>();
		Stack<Integer> nodesToExplore = new Stack<Integer>();
		Stack<Integer> edgesPerNode = new Stack<Integer>();
		
		nodesToExplore.push(nodeID);
		edgesPerNode.push(1);
		
		while(nodesToExplore.size() > 0) {
			nodeID = nodesToExplore.pop();
			node = inputGraph[nodeID];
			
			int edgesRemaining = edgesPerNode.pop();
			edgesRemaining--;
			edgesPerNode.push(edgesRemaining);

			if (node.isExplored() == false) {
				nodesExplored++;
				node.setExplored(true);
				node.setLeader(sourceNodeIndex);
				if (node.edgeList.size() > 0) {
					for (int i = node.edgeList.size()-1; i>=0; i--) {
						nodesToExplore.push(node.edgeList.get(i));
					}
					edgesPerNode.push(node.edgeList.size());
					nodeStack.push(nodeID);
				}
			}
			while((edgesPerNode.size() > 0)&&(edgesPerNode.peek() == 0)) {
				edgesPerNode.pop();
				if (nodeStack.size() > 0) {
					nodeID = nodeStack.pop();
					node = inputGraph[nodeID];
					currentTime++;
					node.setFinishingTime(currentTime);
				}
			}
		}
		return(nodesExplored);
	}

	// Deep print
	public static void myprint(GraphNode[] nodeList) {
		for (int i=0; i<nodeList.length; i++) {
			GraphNode n = nodeList[i];
			if (n!=null) { n.print(Integer.toString(i));; }
		}
	}

}
