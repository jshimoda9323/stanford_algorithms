package Week9p2;

import java.io.File;
import java.util.Scanner;

public class Week9p2 {

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		
		File inFile = new File(args[0]);
		Graph graph;
		
		try (Scanner inScanner = new Scanner(inFile)) {
			String[] line = inScanner.nextLine().split("\\s+");
			graph = new Graph(Integer.parseInt(line[0]),Integer.parseInt(line[1]));
			while(inScanner.hasNextLine()) {
				line = inScanner.nextLine().split("\\s+");
				graph.addEdge(new Edge(Integer.parseInt(line[0]),Integer.parseInt(line[1]),Integer.parseInt(line[2])));
			}
		} catch (Exception e) {
			e.printStackTrace();
	        return;
		}
		
		long cost = graph.computeMST();
		System.out.println("Cost of MST is "+cost);
		
	}

}
