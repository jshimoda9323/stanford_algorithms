package quicksort;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;

public class quicksort {

	public static void main(String[] args) {
        if (args.length != 1) { System.err.println("Expecting input filename."); return; }

        File inFile = new File(args[0]);
        ArrayList<Integer> inputList = new ArrayList<Integer>();
        try (Scanner inScanner = new Scanner(inFile)) {
                while(inScanner.hasNextInt()) { inputList.add(inScanner.nextInt()); }
        } catch (Exception e) {
                e.printStackTrace();
                return;
        }

        int[] sortList = new int[inputList.size()];
        
        // First element pivot
        int i = 0;
        for (Integer n : inputList) { sortList[i++] = n; }
        //printList(sortList,"Original list: ");
        pivotMethodInterface myPivotMethod = new pivotMethodInterface() {
        	public int choosePivot(int[] list,int s, int l) { return(s); }
        };
        long comparisonCount = sort(sortList, 0, sortList.length-1, myPivotMethod);
        System.err.println("First Element Pivot Quick Sort comparisons = "+comparisonCount);
        verifier(sortList);
        printList(sortList,"Sorted list: ");
        
        // Last element pivot
        i = 0;
        for (Integer n : inputList) { sortList[i++] = n; }
        myPivotMethod = new pivotMethodInterface() {
        	public int choosePivot(int[] list, int s, int l) { return(l); }
        };
        comparisonCount = sort(sortList, 0, sortList.length-1, myPivotMethod);
        System.err.println("Last Element Pivot Quick Sort comparisons = "+comparisonCount);
        verifier(sortList);
        printList(sortList,"Sorted list: ");
        
        // Median of three pivot
        i = 0;
        for (Integer n : inputList) { sortList[i++] = n; }
        myPivotMethod = new pivotMethodInterface() {
        	public int choosePivot(int[] list, int s, int l) {
        		int t,mid;
        		mid = (l-s)/2+s;
        		if (list[s] > list[mid]) { t = s; s = mid; mid = t; }
        		if (list[mid] > list[l]) {
        			t=l; l = mid; mid = t;
        			if (list[s] > list[mid]) { t = s; s = mid; mid = t; }
        		}
        		return(mid);
        	}
        };
        comparisonCount = sort(sortList, 0, sortList.length-1, myPivotMethod);
        System.err.println("Median-of-three Pivot Quick Sort comparisons = "+comparisonCount);
        verifier(sortList);
        printList(sortList,"Sorted list: ");
        
        // (10x) Random element pivot
        i = 0;
        for (Integer n : inputList) { sortList[i++] = n; }
        myPivotMethod = new pivotMethodInterface() {
        	public int choosePivot(int[] list, int s, int l) {
        		return(ThreadLocalRandom.current().nextInt(s,l+1));
        	}
        };
        comparisonCount = 0;
        for (int j=0; j<10; j++) {
        	i = 0;
        	for (Integer n : inputList) { sortList[i++] = n; }
        	comparisonCount += sort(sortList, 0, sortList.length-1, myPivotMethod);
        	verifier(sortList);
        }
        System.err.println("Average of 10 Random Pivot Quick Sort comparisons = "+comparisonCount/10);
        //printList(sortList,"Sorted list: ");
	}
	
	public static boolean verifier(int[] list) {
		for (int i=0; i<list.length-1; i++) {
			if (list[i]>list[i+1]) { return(false); }
		}
		System.err.println("Verification OK");
		return(true);
	}
	
	public static void printList(int[] list, String title) {
		System.err.print(title);
        for (int i=0; i<list.length; i++) {
        	System.err.print(list[i]+" ");
        }
        System.err.print("\n");
	}
	
	public interface pivotMethodInterface {
		// Return the index where the median value exists
		public int choosePivot (int[] list, int start, int last);
	}
	
	public static void swap(int[] list, int a, int b) {
		int t = list[a]; list[a] = list[b]; list[b] = t;
	}
	
	public static int partition(int[] list, int start, int last, int pivot) {
		if (pivot != start) { swap(list, start, pivot); pivot = start; }
		int i,j;
		for (i=pivot+1,j=pivot+1; j<=last; j++) {
			if (list[pivot] > list[j]) { swap(list,i,j); i++; }
		}
		swap(list,pivot,i-1);
		return(i-1);
	}
	
	public static long sort(int[] list, int startIndex, int lastIndex, pivotMethodInterface pivotMethod) {
		int pivot = pivotMethod.choosePivot(list,startIndex,lastIndex);
		pivot = partition(list,startIndex,lastIndex,pivot);
		long comparisonCount = lastIndex+1-startIndex-1;
		if ((pivot-1)>startIndex) {
			comparisonCount += sort(list,startIndex,pivot-1,pivotMethod);
		}
		if (lastIndex>(pivot+1)) {
			comparisonCount += sort(list,pivot+1,lastIndex,pivotMethod);
		}
		return(comparisonCount);	
	}

}
