package week5_scc;

import java.util.*;

public class GraphNode {
	
	ArrayList<Integer> edgeList;
	boolean explored;
	int leader;
	int finishingTime;

	public GraphNode() {
		explored = false;
		edgeList = new ArrayList<Integer>();
		finishingTime = 0;
		leader = 0;
	}
	
	public boolean setExplored(boolean b) { explored = b; return(explored); }
	public boolean isExplored() { return(explored); }
	
	public int getFinishingTime() { return(finishingTime); }
	public int setFinishingTime(int f) { finishingTime = f; return(finishingTime); }
	
	public int setLeader(int l) { leader = l; return(leader); }
	public int getLeader() { return(leader); }
	
	public boolean addEdge(Integer edge) {
		return(edgeList.add(edge));
	}
	
	public void print(String id) {
		System.err.println("explored="+explored);
		System.err.println("leader="+leader);
		if (id!=null) { System.err.print(id+": "); }
		for (Integer i: edgeList) {
			System.err.print(i+" ");
		}
		System.err.println();
	}

}
