package week8_2sum;

import java.util.*;
import java.lang.Math;

public class crappyhash {
	
	HashMap<Long,HashMap<Long,Integer>> hmap;

	public crappyhash() {
		hmap = new HashMap<Long,HashMap<Long,Integer>>();
	}
	
	public void put(Long i) {
		Long bucket = Math.abs(i) / 10000;
		if (hmap.containsKey(bucket)) {
			HashMap<Long,Integer> bucketHash = hmap.get(bucket);
			if (!bucketHash.containsKey(i)) {
				bucketHash.put(i,1);
				hmap.replace(bucket, bucketHash);
			}
		} else {
			HashMap<Long,Integer> bucketHash = new HashMap<Long,Integer>();
			bucketHash.put(i,1);
			hmap.put(bucket, bucketHash);
		}
	}
	
	public HashMap<Long,Integer> getBucket(Long bucket) {
		return(hmap.get(bucket));
	}
	
	public Set<Long> getKeys() {
		return(hmap.keySet());
	}
	
	

}
