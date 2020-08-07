package countInversions;

import java.util.*;
import java.io.*;

public class countInversions {

	public static void main(String[] args) {
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }

		File inFile = new File(args[0]);
		ArrayList<Integer> inputList = new ArrayList<Integer>();
		try (Scanner inScanner = new Scanner(inFile)) {
			while(inScanner.hasNextLine()) {
				String inLine = inScanner.nextLine();
				inputList.add(Integer.valueOf(inLine));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		int[] sortList = new int[inputList.size()];
		int i = 0;
		for (Integer n : inputList) { sortList[i++] = n; }
		inversionDataClass inversionData = mergeSortAndInversionCount(sortList);

		System.err.println("inversionCount="+inversionData.inversionCount);
	}
	public static inversionDataClass mergeSortAndInversionCount(int[] inputList) {
		inversionDataClass result = new inversionDataClass();
		if ((inputList == null)||(inputList.length == 1)) {
			result.list = inputList;
		} else if (inputList.length == 2) {
			/* Sort these two elements */
			if (inputList[0] > inputList[1]) {
				result.list = new int[2];
				result.list[0] = inputList[1];
				result.list[1] = inputList[0];
				result.inversionCount = 1;
			} else {
				result.list = inputList;
				result.inversionCount = 0;
			}
		} else {
			/* Split the list, recurse on each side */
			inversionDataClass left = mergeSortAndInversionCount(Arrays.copyOfRange(inputList,0,inputList.length/2));
			inversionDataClass right = mergeSortAndInversionCount(Arrays.copyOfRange(inputList,inputList.length/2,inputList.length));
			/* Merge the two halves */
			result = mergeAndInversionCount(left.list,right.list);
			result.inversionCount = result.inversionCount + left.inversionCount + right.inversionCount;
		}
		return(result);
	}

	public static inversionDataClass mergeAndInversionCount(int[] left, int[] right) {
		inversionDataClass result = new inversionDataClass();
		if ((left == null)&&(right == null)) { result.list = null; }
		else if (left == null) { result = new inversionDataClass(); result.list = right; }
		else if (right == null) { result = new inversionDataClass(); result.list = left; }
		else {
			int i;
			int j;
			result = new inversionDataClass();
	        result.list = new int[left.length+right.length];
			for (i=0,j=0;((i<left.length)||(j<right.length));) {
				if ((i<left.length)&&(j<right.length)) {
					if (left[i] > right[j]) {
						result.list[i+j] = right[j];
						j++;
					}
					else {
						result.list[i+j] = left[i];
						i++;
						result.inversionCount = result.inversionCount + j;
					}
				} else if (i<left.length) {
					/* Only left elements remain */
					result.list[i+j] = left[i];
					i++;
					result.inversionCount = result.inversionCount + j;
				} else {
					/* Only right elements remain */
					result.list[i+j] = right[j];
					j++;
				}
			}
		}
		return(result);
	}
}
