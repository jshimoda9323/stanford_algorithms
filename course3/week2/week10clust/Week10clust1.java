package Week10clust1;

import java.io.File;
import java.util.Scanner;

public class Week10clust1 {

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		File inFile = new File(args[0]);
		Graph graph;
		try (Scanner inScanner = new Scanner(inFile)) {
			String[] line = inScanner.nextLine().split("\\s+");
			graph = new Graph(Integer.parseInt(line[0]));
			while(inScanner.hasNextLine()) {
				line = inScanner.nextLine().split("\\s+");
				graph.setNextEdge(Integer.parseInt(line[0]),Integer.parseInt(line[1]),Integer.parseInt(line[2]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		int cost = graph.computeKCluster(4);
		System.out.println(cost);
	}

}
