package Week9;

import java.util.Arrays;

public class Schedule {
	
	Job[] schedule;

	public Schedule(int sz) {
		schedule = new Job[sz];
	}
	
	public int getSize() {
		return(schedule.length);
	}
	
	public void setJob(Job a, int idx) {
		schedule[idx] = a;
	}
	
	public void sortByScoreDiff() {
		Arrays.sort(schedule, new JobScoreDiffComparator());
	}
	
	public void sortByScoreRatio() {
		Arrays.sort(schedule, new JobScoreRatioComparator());
	}
	
	public void printByDiff() {
		for (int i = 0; i < schedule.length; i++) {
			System.out.println("["+i+"] "+schedule[i].getScoreDiff()+" Weight="+schedule[i].getWeight()+" Length="+schedule[i].getLength());
		}
	}
	
	public void printByRatio() {
		for (int i = 0; i < schedule.length; i++) {
			System.out.println("["+i+"] "+schedule[i].getScoreRatio()+" Weight="+schedule[i].getWeight()+" Length="+schedule[i].getLength());
		}
	}
	
	public long computeWeightedCompletion() {
		long total = 0;
		schedule[0].setCompletionTime(schedule[0].getLength());
		total = schedule[0].getWeight()*schedule[0].getLength();
		for (int i = 1; i < schedule.length; i++) {
			schedule[i].setCompletionTime(schedule[i].getLength()+schedule[i-1].getCompletionTime());
			total += schedule[i].getWeight()*schedule[i].getCompletionTime();
		}
		return(total);
	}

}
