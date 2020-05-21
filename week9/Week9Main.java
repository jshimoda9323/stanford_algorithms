package Week9;

import java.io.File;
import java.util.Scanner;

public class Week9Main {

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		
		Schedule schedule;
		File inFile = new File(args[0]);
		int jobIdx = 0;
		long total;
		try (Scanner inScanner = new Scanner(inFile)) {
			schedule = new Schedule(Integer.parseInt(inScanner.nextLine()));
			while(inScanner.hasNextLine()) {
				String[] line = inScanner.nextLine().split("\\s+");
				schedule.setJob(new Job(Integer.parseInt(line[0]),Integer.parseInt(line[1])), jobIdx);
				jobIdx++;
			}
		} catch (Exception e) {
	        e.printStackTrace();
	        return;
		}
		
		schedule.sortByScoreDiff();
		total = schedule.computeWeightedCompletion();
		System.out.println("Sum of weighted completion times by scoreDiff: "+total);
		schedule.sortByScoreRatio();
		total = schedule.computeWeightedCompletion();
		System.out.println("Sum of weighted completion times by scoreRatio: "+total);
		
	}

}
