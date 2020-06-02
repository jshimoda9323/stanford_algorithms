package week11q3;

import java.io.File;
import java.util.Scanner;

public class week11q3 {
	
	public static void main(String[] args) {
		Node[] nodes;
		
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			nodes = new Node[Integer.parseInt(inScanner.nextLine())+1];
			int nodeIdx = 1;
			while(inScanner.hasNextLine()) {
				nodes[nodeIdx] = new Node(Integer.parseInt(inScanner.nextLine()));
				nodeIdx++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		int[][] MWIS = computeMWIS(nodes);
		int[] printNodeIndexes = {1, 2, 3, 4, 17, 117, 517, 997};
		for (int i : printNodeIndexes) {
			if (MWIS[1][i] == 2) {
				System.out.print("1");
			} else {
				System.out.print("0");
			}
		}
		System.out.println("");
	}
	
	public static int[][] computeMWIS(Node[] nodes) {
		int[][] MWIS = new int[2][nodes.length];
		MWIS[0][0] = 0;
		MWIS[1][1] = 1;
		MWIS[0][1] = nodes[1].weight;
		MWIS[1][1] = 2;
		for (int i = 2; i < nodes.length; i++) {
			int c1 = MWIS[0][i-1];
			int c2 = MWIS[0][i-2]+nodes[i].weight;
			if (c1 >= c2) {
				MWIS[0][i] = c1;
				MWIS[1][i] = 1;
			} else {
				MWIS[0][i] = c2;
				MWIS[1][i] = 2;
			}
		}
		return(MWIS);
	}

}
