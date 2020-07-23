package c4week1;

import java.util.*;

public class Node {
	
	public int weight;
	public ArrayList<Edge> inEdges;
	public ArrayList<Edge> outEdges;
	public int id;
	
	public Node(int identifier) {
		id = identifier;
		weight = 0;
		inEdges = new ArrayList<Edge>();
		outEdges = new ArrayList<Edge>();
	}
	
	public void addIncomingEdge(Edge e) {
		if (e.dst == id) {
			inEdges.add(e);
		}
	}
	
	public void addOutgoingEdge(Edge e) {
		if (e.src == id) {
			outEdges.add(e);
		}
	}
}
