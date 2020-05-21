package Week9;

public class Job {
	
	int weight;
	int length;
	long completionTime;
	
	int scoreDiff;
	float scoreRatio;

	public Job(int w, int l) {
		weight = w;
		length = l;
		scoreDiff = w - l;
		scoreRatio = ((float)w/(float)l);
		completionTime = -1;
	}
	
	public int getScoreDiff() {
		return(scoreDiff);
	}
	
	public float getScoreRatio() {
		return(scoreRatio);
	}
	
	public int getWeight() {
		return(weight);
	}
	
	public int getLength() {
		return(length);
	}
	
	public void setCompletionTime(long c) {
		completionTime = c;
	}
	
	public long getCompletionTime() {
		return(completionTime);
	}

}
