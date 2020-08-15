package median_maintenance;

import java.util.*;
import java.io.File;

public class median_maintenance {

	public static void main(String[] args) {
		int total;
		PriorityQueue<Integer> heapMin = new PriorityQueue<Integer>();
		PriorityQueue<Integer> heapMax = new PriorityQueue<Integer>(new ReverseIntegerComparator());
		
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			total = 0;
			// Initialize heapMax with an element to keep maintain_median efficient (don't have to check that heapMax.isEmpty on every call)
			if (inScanner.hasNextLine()) {
				Integer n = Integer.parseInt(inScanner.nextLine());
				heapMax.add(n);
				total += n;
			}
			
	        while(inScanner.hasNextLine()) {
	        	Integer n = Integer.parseInt(inScanner.nextLine());
	        	total += maintain_median(heapMin,heapMax,n);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println("total%10000="+(total%10000));

	}
	
	// We assume heapMax contains at least one element
	public static Integer maintain_median(PriorityQueue<Integer> heapMin, PriorityQueue<Integer> heapMax, Integer n) {
		if (n <= heapMax.peek()) { heapMax.add(n); }
		else { heapMin.add(n); }
		if (heapMax.size() > heapMin.size()+1) { heapMin.add(heapMax.poll()); }
		else if (heapMin.size() > heapMax.size()) { heapMax.add(heapMin.poll()); }
		return(heapMax.peek());
	}
}
