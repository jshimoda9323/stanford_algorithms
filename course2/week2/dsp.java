package dsp;

import java.util.*;
import java.io.File;

public class dsp {

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
			
		Graph graph = new Graph();
		
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
          while(inScanner.hasNextLine()) {
        	String line = inScanner.nextLine();
        	String[] lineElements = line.split("\\s+");
        	GraphNode n = new GraphNode();
        	int nodeID = Integer.parseInt(lineElements[0]);
        	for (int i = 1; i < lineElements.length; i++) {
        		String[] destLenPair = lineElements[i].split(",");
        		n.destinations.add(new NodeReference(Integer.parseInt(destLenPair[0]),Integer.parseInt(destLenPair[1]),nodeID));
        	}
        	graph.addNode(n);
          }
		} catch (Exception e) {
		        e.printStackTrace();
		        return;
		}
		
		dijkstraShortestPath(graph,1);
		
		graph.getNode(7).printDistance();
		graph.getNode(37).printDistance();
		graph.getNode(59).printDistance();
		graph.getNode(82).printDistance();
		graph.getNode(99).printDistance();
		graph.getNode(115).printDistance();
		graph.getNode(133).printDistance();
		graph.getNode(165).printDistance();
		graph.getNode(188).printDistance();
		graph.getNode(197).printDistance();
	}
	
	static void dijkstraShortestPath(Graph g, int sourceID) {
		PriorityQueue<NodeReference> frontier = new PriorityQueue<NodeReference>(new NodeReferenceComparator());
		
		// Initialize the source node
		GraphNode sourceNode = g.getNode(sourceID);
		sourceNode.processed = true;
		sourceNode.computedDistance = 0;
		for (NodeReference d : sourceNode.destinations) {
			d.score = d.weight;
			frontier.add(d);
		}
		
		while (!frontier.isEmpty()) {
			NodeReference smallest = frontier.poll();
			GraphNode destNode = g.getNode(smallest.id);
			
			if (destNode.processed) { continue; }
			
			destNode.computedDistance = smallest.score;
			destNode.processed = true;
			
			for (NodeReference e : destNode.destinations) {
				if (g.getNode(e.id).processed == false) {
					e.score = g.getNode(e.source).computedDistance + e.weight;
					frontier.add(e); }
			}
		}
	}

}
