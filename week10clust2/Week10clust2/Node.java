package Week10clust2;

import java.lang.StringBuffer;

public class Node {
	
	public int nodeLabel;
	public int leaderIdx;
	
	private static final int setOneMask = 0x0001;
	
	private static final int[] SELECT_BITS = {
			0x80000000,
			0x40000000,
			0x20000000,
			0x10000000,
			0x08000000,
			0x04000000,
			0x02000000,
			0x01000000,
			0x00800000,
			0x00400000,
			0x00200000,
			0x00100000,
			0x00080000,
			0x00040000,
			0x00020000,
			0x00010000,
			0x00008000,
			0x00004000,
			0x00002000,
			0x00001000,
			0x00000800,
			0x00000400,
			0x00000200,
			0x00000100,
			0x00000080,
			0x00000040,
			0x00000020,
			0x00000010,
			0x00000008,
			0x00000004,
			0x00000002,
			0x00000001
	}; 

	public Node(int nodeL) {
		nodeLabel = nodeL;
		leaderIdx = -1;
	}
	
	public static int parseStringArrayIntoLabel(String[] inString) {
		int label;
		label = 0;
		for (int i = 0; i < inString.length; i++) {
			if (inString[i].charAt(0) == '0') {
				label = label << 1;
			} else {
				label = ((label << 1)|setOneMask);
			}
		}
		return(label);
	}
	
	public static String intToString(int input, int bitSize) {
		StringBuffer sbuf = new StringBuffer(bitSize);
		int labelbuf = input;
		for (int i = 0; i < bitSize; i++) {
			if ((labelbuf & setOneMask) > 0) {
				sbuf.insert(0, '1');
			} else {
				sbuf.insert(0, '0');
			}
			labelbuf = labelbuf >> 1;
		}
		return(sbuf.toString());
	}
	
	public String labelToString(int bitSize) {
		return(intToString(nodeLabel,bitSize));
	}
	
	public static int nChooseR(int n, int r) {
		int ret = n;
		for (int i = n-1; i > n-r; i--) {
			ret = ret*i;
		}
		for (int i = r; i > 0; i--) {
			ret = ret/i;
		}
		return(ret);
	}
	
	public static int getHammingDistance(int labelA, int labelB) {
		int result = labelA ^ labelB;
		int actualDist= 0;
		for (int i = 0; i < 32; i++) {
			if ((result & setOneMask) > 0) {
				actualDist += 1;
			}
			result = result >> 1;
		}
		return actualDist;
	}
	
	public int[] computePossibleNeighbors(int labelSize) {
		int[] neighbors = new int[1+labelSize+nChooseR(labelSize,2)];
		neighbors[0] = nodeLabel;
		int nIdx = 1;
		// Get all 1-distance neighbors
		for (int i = SELECT_BITS.length-labelSize; i < SELECT_BITS.length; i++) {
			neighbors[nIdx] = nodeLabel ^ SELECT_BITS[i];
			nIdx++;
		}
		// Get all 2-distance neighbors
		for (int i = SELECT_BITS.length-labelSize; i < SELECT_BITS.length; i++) {
			int d1label = nodeLabel ^ SELECT_BITS[i];
			for (int j = i+1; j < SELECT_BITS.length; j++) {
				neighbors[nIdx] = d1label ^ SELECT_BITS[j];
				nIdx++;
			}
		}
		return(neighbors);
	}

}
