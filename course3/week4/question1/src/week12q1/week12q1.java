package week12q1;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class week12q1 {

	public static void main(String[] args) {
		int totalSize;
		int totalItems;
		int[][] items;
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			String[] line = inScanner.nextLine().split("\\s+");
			totalSize = Integer.parseInt(line[0]);
			totalItems = Integer.parseInt(line[1]);
			items = new int[totalItems][2];
			int itemIdx = 0;
			while(inScanner.hasNextLine()) {
				line = inScanner.nextLine().split("\\s+");
				items[itemIdx][0] = Integer.parseInt(line[0]);  // Value
				items[itemIdx][1] = Integer.parseInt(line[1]);  // Weight
				itemIdx++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		long maxValue = knapsack(items, totalSize);
		System.out.println(maxValue);

	}
	
	public static long knapsack(int[][] items, int totalSize) {
		long maxValue;
		long[][] maxValues = new long[2][totalSize+1];
		
		// Sort the item array by weight from highest to lowest
		Arrays.sort(items, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) { return(Integer.compare(b[1], a[1])); }
		});

		// Initialize the arrays
		Arrays.fill(maxValues[0], 0);
		Arrays.fill(maxValues[1], 0);
		
		// Main item loop
		for (int i = 0; i < items.length; i++) {
			int oidx = i % 2;
			int nidx = (i + 1) % 2;
			for (int x = items[i][1]; x <= totalSize; x++) {
				long c1 = maxValues[oidx][x];
				long c2 = maxValues[oidx][x-items[i][1]] + items[i][0];
				maxValues[nidx][x] = Math.max(c1, c2);
			}
		}
		if ((items.length-1) % 2 == 0) {
			maxValue = maxValues[1][totalSize];
		} else {
			maxValue = maxValues[0][totalSize];
		}
		return(maxValue);
	}
}
