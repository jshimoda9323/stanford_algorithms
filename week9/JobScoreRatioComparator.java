package Week9;

import java.util.Comparator;

public class JobScoreRatioComparator implements Comparator<Job> {

	public int compare(Job a, Job b) {
		//return(a.scoreRatio < b.scoreRatio ? -1 : a.scoreRatio > b.scoreRatio ? 1 : 0);  // Ascending
		return(a.scoreRatio > b.scoreRatio ? -1 : a.scoreRatio < b.scoreRatio ? 1 : 0);  // Descending
	}

}
