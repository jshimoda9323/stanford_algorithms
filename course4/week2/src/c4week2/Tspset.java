package c4week2;

public class Tspset {
	private int[] setVars;
	public boolean validNextState;
	private int setSize;
	private int maxSetSize;
	
	public static int[] bitMasks = {
			0x1,
			0x2,
			0x4,
			0x8,
			0x10,
			0x20,
			0x40,
			0x80,
			0x100,
			0x200,
			0x400,
			0x800,
			0x1000,
			0x2000,
			0x4000,
			0x8000,
			0x10000,
			0x20000,
			0x40000,
			0x80000,
			0x100000,
			0x200000,
			0x400000,
			0x800000,
			0x1000000,
			0x2000000,
			0x4000000,
			0x8000000,
			0x10000000,
			0x20000000,
			0x40000000,
			0x80000000
			};
	
	public Tspset(int setSize, int maxSetSize) {
		this.setSize = setSize;
		this.maxSetSize = maxSetSize;
		setVars = new int[setSize-1];
		validNextState = true;
		for (int i = 0; i < setVars.length; i++) {
			setVars[i] = bitMasks[setSize-1-i];
		}
	}
	
	// Returns the set state, an integer whose bits are the set's contents.
	public int getNextState() {
		int retState = bitMasks[0];
		int i;
		// Construct the state to return
		for (i = 0; i < setVars.length; i++) {
			retState = retState | setVars[i];
		}
		// Calculate the next state
		i = 0;
		setVars[0] = setVars[0] << 1;
		while (setVars[i] > bitMasks[maxSetSize-1-i]) {
			if (i < setVars.length - 1) {
				setVars[i+1] = setVars[i+1] << 1;
				for (int j = i; j >=0; j--) {
					setVars[j] = setVars[j+1] << 1;
				}
				i++;
			} else {
				validNextState = false;
				break;
			}
		}
		return(retState);
	}
	
	public String printSet(int set) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < Integer.BYTES*8; i++) {
			if ((set & bitMasks[i]) != 0) {
				s.append(","+i);
			}
		}
		return(s.toString());
	}
	
	// Returns a list of integers representing the set, given the set state.
	public int[] getSet(int setState) {
		int[] retSet = new int[setSize];
		int j = 0;
		for (int i = 0; i < Integer.BYTES*8; i++) {
			if ((setState & bitMasks[i]) != 0) {
				retSet[j] = i;
				j++;
			}
		}
		return(retSet);
	}

}
