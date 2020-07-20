package c4week4;

import java.io.File;
import java.util.*;

public class c4week4 {
	
	static int currentTime;
	static int sourceNodeIndex;

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		
		int numVariables;
		ImplicationGraph forwardGraph;
		ImplicationGraph backwardGraph;
		int[] processingOrder;
		int[] finishingTimes;
		
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			numVariables = Integer.parseInt(inScanner.nextLine());
			forwardGraph = new ImplicationGraph(numVariables);
			backwardGraph = new ImplicationGraph(numVariables);
			
			while(inScanner.hasNextLine()) {
				String[] line = inScanner.nextLine().split("\\s+");
				int varA = Integer.parseInt(line[0]);
				int varB = Integer.parseInt(line[1]);
				forwardGraph.addClause(varA, varB);
				backwardGraph.addClause(varA*-1, varB*-1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// Processing order over backward graph is arbitrary
		processingOrder = new int[backwardGraph.nodes.length];
		for (int n = 0; n < processingOrder.length; n++) {
			processingOrder[n] = n;
		}
		
		// Perform DFS to obtain finishing times
		finishingTimes = DFS_Loop(backwardGraph,processingOrder);
		
		// Final DFS to find leaders
		DFS_Loop(forwardGraph,finishingTimes);
		
		// Determine if any variable shares the same leader
		int answer = 1;
		for (int n = 1; n <= forwardGraph.numVariables; n++) {
			int varIndex = n;
			int notVarIndex = n + forwardGraph.numVariables;
			if (forwardGraph.nodes[varIndex].leaderIndex == forwardGraph.nodes[notVarIndex].leaderIndex) {
				answer = 0;
				break;
			}
		}
		
		System.out.println(answer);
		
	}
	
	// Calls DFS in order of nodes in processingOrder[n->0]
	// Returns the finishing times.
	public static int[] DFS_Loop(ImplicationGraph graph, int[] processingOrder) {
		int[] finishingTimes = new int[graph.nodes.length];
		Arrays.fill(finishingTimes, 0);
		currentTime = 0;
		// Perform Depth-First Search on every node that is not yet explored.
		for (int n = graph.nodes.length - 1; n > 0; n--) {
			// Retrieve the node to be processed
			//if (finishingTimes[processingOrder[n]] == 0) {
			if (!graph.nodes[processingOrder[n]].isExplored) {
				sourceNodeIndex = n;
				DFS(graph,processingOrder[n],finishingTimes);
			}
		}
		return(finishingTimes);
	}
	
	public static int DFS(ImplicationGraph graph, int nodeIndex, int[] finishingTimes) {
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
