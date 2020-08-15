package dsp;

import java.util.*;

public class GraphNode {
	
	public ArrayList<NodeReference> destinations;
	public int computedDistance;
	public boolean processed;

	public GraphNode() {
		destinations = new ArrayList<NodeReference>();
		computedDistance = -1;
		processed = false;
	}
	
	public void print() {
		for (NodeReference destination : destinations) {
			System.out.print(destination.id + ","+destination.weight+" ");
		}
		System.out.println("");
	}
	
	public void printDistance() {
		System.out.println(computedDistance);
	}


}
