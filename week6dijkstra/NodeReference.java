package dsp;

public class NodeReference {
	
	public int id;
	public int weight;
	public int source;
	public int score;

	public NodeReference(int i, int w, int s) {
		id = i;
		weight = w;
		source = s;
		score = 0;
	}

}
