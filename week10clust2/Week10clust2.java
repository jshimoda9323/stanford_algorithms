package Week10clust2;

import java.io.File;
import java.util.Scanner;

public class Week10clust2 {

	public static void main(String[] args) {
		
		int nodeCount;
		int bitCount;
		
		Graph graph;
		
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			String[] line = inScanner.nextLine().split("\\s+");
			nodeCount = Integer.parseInt(line[0]);
			bitCount = Integer.parseInt(line[1]);
			graph = new Graph(nodeCount,bitCount);
			while(inScanner.hasNextLine()) {
				Node newNode = new Node(Node.parseStringArrayIntoLabel(inScanner.nextLine().split("\\s+")));
				graph.addNode(newNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		graph.computeThreeSpacing();
		System.out.println(graph.clusterCount);
		
		
	}

}
