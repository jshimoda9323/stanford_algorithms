package dsp;

import java.util.*;

public class Graph {
	
	ArrayList<GraphNode> nodes;

	public Graph() {
		nodes = new ArrayList<GraphNode>();
		nodes.add(null);
	}
	
	public int getSize() {
		return(nodes.size()-1);
	}
	
	public GraphNode getNode(int id) {
		return(nodes.get(id));
	}
	
	public int addNode(GraphNode n) {
		nodes.add(n);
		return(nodes.size()-1);
	}
	
	public void print() {
		for (int i = 1; i < nodes.size(); i++) {
			System.out.print("Node ["+i+"] : ");
			nodes.get(i).print();
			System.out.println("");
		}
	}

}
