package week8_2sum;

import java.io.File;
import java.util.*;

public class TwoSum {

	public static void main(String[] args) {
		crappyhash hmap = new crappyhash();
		Integer targetValueCount = 0;
		boolean[] range = new boolean[20001];
		
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
	        while(inScanner.hasNextLine()) {
	        	Long l = Long.parseLong(inScanner.nextLine());
	        	hmap.put(l);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		HashMap<Long,Integer> bucketA;
		HashMap<Long,Integer> bucketB;
		HashMap<Long,Integer> bucketC;
		
		Set<Long> buckets = hmap.getKeys();
		Long[] lbuckets = new Long[buckets.size()];
		lbuckets = buckets.toArray(lbuckets);
		
		System.out.println("Total buckets="+lbuckets.length);
		bucketA = null;
		bucketB = new HashMap<Long,Integer>();
		bucketC = new HashMap<Long,Integer>();
		
		for (Long bucket : hmap.getKeys()) {
			bucketA = bucketB;
			bucketB = bucketC;
			bucketC = hmap.getBucket(bucket);

			HashMap<Long,Integer> window = new HashMap<Long,Integer>();
			window.putAll(bucketA);
			window.putAll(bucketB);
			window.putAll(bucketC);
			
			if (window.keySet().size()*window.keySet().size() >= 10000) {
				System.out.println("This can be more efficient.");
				return;
			}
			for (Long key1 : window.keySet()) {
				for (Long key2 : window.keySet()) {
					if (key1 != key2) {
						Long rangeIdx = key1+key2+10000;
						if ((rangeIdx >= 0)&&(rangeIdx < 20001)) {
							range[rangeIdx.intValue()] = true;
						}
					}
				}
			}
		}
		
		for (int rangeIdx = 0; rangeIdx < range.length; rangeIdx++) {
			if (range[rangeIdx]) {
				targetValueCount++;
			}
		}
		System.out.println("Target Count = "+targetValueCount);
	}

}
