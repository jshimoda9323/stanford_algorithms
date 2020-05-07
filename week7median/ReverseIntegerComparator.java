package median_maintenance;

import java.util.Comparator;

public class ReverseIntegerComparator implements Comparator<Integer> {
	public int compare(Integer a, Integer b) {
		return(b < a ? -1 : b > a ? 1 : 0);
	}
}
