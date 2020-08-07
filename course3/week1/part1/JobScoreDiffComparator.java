package Week9;

import java.util.Comparator;

public class JobScoreDiffComparator implements Comparator<Job> {

	public int compare(Job a, Job b) {
		//return(a.scoreDiff < b.scoreDiff ? -1 : a.scoreDiff > b.scoreDiff ? 1 : a.weight < b.weight ? -1 : a.weight > b.weight ? 1 : 0); // Ascending
		return(a.scoreDiff > b.scoreDiff ? -1 : a.scoreDiff < b.scoreDiff ? 1 : a.weight > b.weight ? -1 : a.weight < b.weight ? 1 : 0); // Descending
	}

}
