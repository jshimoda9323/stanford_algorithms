package week5_scc2;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class scc2 {
	
	static int currentTime;
	static final int inputSize = 875714+1;

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		
		Graph graphForward = new Graph(inputSize);
		Graph graphBackward = new Graph(inputSize);
		int[] processingOrder;
		int[] finishingTimes;
		
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			while(inScanner.hasNextLine()) {
				String line = inScanner.nextLine();
	        	String[] lineElements = line.split("\\s+");
	        	int inputNodeNum = Integer.parseInt(lineElements[0]);
	        	int inputEdge = Integer.parseInt(lineElements[1]);
	        	graphForward.nodes[inputNodeNum].addEdge(inputEdge);
	        	graphBackward.nodes[inputEdge].addEdge(inputNodeNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
	        return;
		}
		
		// Processing order over backward graph is arbitrary
		processingOrder = new int[graphBackward.nodes.length];
		for (int n = 0; n < processingOrder.length; n++) {
			processingOrder[n] = n;
		}
		
		// Perform DFS to obtain finishing times
		finishingTimes = DFS_Loop(graphBackward,processingOrder);
		
		// Final DFS to find leaders
		DFS_Loop(graphForward,finishingTimes);
		
		// Find the 5 largest SCCs
		int[] sccSize = new int[graphForward.nodes.length];
		Arrays.fill(sccSize, 0);
		for (int n = 1; n < graphForward.nodes.length; n++) {
			sccSize[graphForward.nodes[n].leaderIndex]++;
		}
		Arrays.sort(sccSize);
		System.out.println(sccSize[sccSize.length-1]+","+sccSize[sccSize.length-2]+","+sccSize[sccSize.length-3]+","+sccSize[sccSize.length-4]+","+sccSize[sccSize.length-5]);
		
	}
	
	// Calls DFS in order of nodes in processingOrder[n->0]
	// Returns the finishing times.
	public static int[] DFS_Loop(Graph graph, int[] processingOrder) {
		int[] finishingTimes = new int[graph.nodes.length];
		Arrays.fill(finishingTimes, 0);
		currentTime = 0;
		// Perform Depth-First Search on every node that is not yet explored.
		for (int n = graph.nodes.length - 1; n > 0; n--) {
			// Retrieve the node to be processed
			//if (finishingTimes[processingOrder[n]] == 0) {
			if (!graph.nodes[processingOrder[n]].isExplored) {
				DFS(graph,processingOrder[n],finishingTimes);
			}
		}
		return(finishingTimes);
	}
	
	public static int DFS(Graph graph, int nodeIndex, int[] finishingTimes) {
		Stack<Integer> nodeIndexStack = new Stack<Integer>();
		Stack<Integer> nodeIndexesExplored = new Stack<Integer>();
		nodeIndexStack.push(nodeIndex);
		int nodesExplored = 0;
		while (nodeIndexStack.size() > 0) {
			int currNodeIndex = nodeIndexStack.pop();
			if (graph.nodes[currNodeIndex].isExplored) {
				continue;
			}
			
			nodeIndexesExplored.push(currNodeIndex);
			graph.nodes[currNodeIndex].isExplored = true;
			graph.nodes[currNodeIndex].leaderIndex = nodeIndex;
			nodesExplored++;
			
			for (Integer e : graph.nodes[currNodeIndex].outEdges) {
				if (!graph.nodes[e].isExplored) {
					nodeIndexStack.push(e);
				}
			}
		}
		while (nodeIndexesExplored.size() > 0) {
			int currNodeIndex = nodeIndexesExplored.pop();
			currentTime++;
			finishingTimes[currentTime] = currNodeIndex;
		}
		return(nodesExplored);
	}

}
