
import java.util.Arrays;

class largeMult {
	static int depth;

	public static void main(String[] args) {
		if (args.length != 2) { System.err.println("Expecting two integers.\n"); return; }
		if (!checkStringIsInt(args[0])) { System.err.println("Expecting two integers.\n"); return; }
		if (!checkStringIsInt(args[1])) { System.err.println("Expecting two integers.\n"); return; }

		//System.err.println("First arg='"+args[0]+"'");
		//System.err.println("Second arg='"+args[1]+"'");

		/* Convert strings to character arrays */
		char[] arg0 = args[0].toCharArray();
		char[] arg1 = args[1].toCharArray();

		char[] result;
//	        result = add(arg0,arg1);
//		System.err.println("Add result is: "+Arrays.toString(result));
//		result = subtract(arg0,arg1);
		depth = 0;
		result = mult(arg0,arg1);
		System.out.println(new String(result));

	}

	public static char[] mult(char[] op1, char[] op2) {
		char[] result;
		char[] zero = {'0'};
		depth++;
		char[] cSpaces = new char[depth];
		for (int i=0; i<cSpaces.length; i++) { cSpaces[i] = ' '; }
		String spaces = new String("depth="+depth+new String(cSpaces));
		System.err.println(spaces+"mult(): "+new String(op1)+" * "+new String(op2));

		if ((op1 == null)||(op2 == null)||(op1.length<1)||(op2.length<1)||isZero(op1)||isZero(op2)) {
			result = zero;
		} else if (isSingle(op1)&&isSingle(op2)) {
		        result = multSingle(op1[op1.length-1],op2[op2.length-1]);
		} else {
			int digits;
			char[] a,b,c,d,ac,bd,t;

			op1 = stripLeadingZeros(op1);
			op2 = stripLeadingZeros(op2);
			digits = Math.max(op1.length,op2.length);
			if (digits%2==1) { digits += 1; }
			System.err.println(spaces+"mult(): digits="+digits);
			op1 = padLeadingZeros(op1,digits);
			op2 = padLeadingZeros(op2,digits);
			System.err.println(spaces+"mult(): op1="+new String(op1)+" op2="+new String(op2));


			/* find a,b,c,d */
			int half = digits/2;
			a = Arrays.copyOfRange(op1,0,half);
			b = Arrays.copyOfRange(op1,half,digits);
			c = Arrays.copyOfRange(op2,0,half);
			d = Arrays.copyOfRange(op2,half,digits);

			System.err.println(spaces+"mult(): a="+new String(a));
			System.err.println(spaces+"mult(): b="+new String(b));
			System.err.println(spaces+"mult(): c="+new String(c));
			System.err.println(spaces+"mult(): d="+new String(d));

			/* find ac and bd */
			ac = mult(a,c);
			System.err.println(spaces+"mult() ac="+new String(ac));
			bd = mult(b,d);
			System.err.println(spaces+"mult() bd="+new String(bd));


			/* find t = ac+ad+bc+cd = (a+b)(c+d) */
			char[] t1=add(a,b);
			char[] t2=add(c,d);
			t = mult(t1,t2);
			System.err.println(spaces+"mult(): t=(a+b)(c+d)="+new String(t1)+" * "+new String(t2)+"="+new String(t));

			/* find t2 = t - ac - bd */
			t1 = subtract(t,ac);
			System.err.println(spaces+"mult(): t1=t-ac="+new String(t)+" - "+new String(ac)+" = "+new String(t1));
			t2 = subtract(t1,bd);
			System.err.println(spaces+"mult(): t2=t1-bd="+new String(t1)+" - "+new String(bd)+" = "+new String(t2));

			/* Shift ac by digits and t2 by digits/2, then add all together */
			t1 = shiftLeft(ac,digits);
			System.err.println(spaces+"mult(): t1=shiftLeft(ac)="+new String(t1));
			t2 = shiftLeft(t2,digits/2);
			System.err.println(spaces+"mult(): t2=shiftLeft(t2)="+new String(t2));
			t = add(t1,t2);
			System.err.println(spaces+"mult(): add(t1,t2)="+new String(t));
			result = add(t,bd);
		}
		depth--;
		System.err.println(spaces+"mult(): result="+new String(op1)+" * "+new String(op2)+ " = "+new String(result));
		return(result);
	}

	public static boolean isSingle(char[] in) {
		if (in == null) { return(false); }
		if (in.length == 1) { return(true); }
		for (int i=0; i<in.length-1; i++) {
			if (in[i] != '0') { return(false); }
		}
		return(true);

	}

	public static char[] multSingle(char c1, char c2) { return(Integer.toString(Character.getNumericValue(c1) * Character.getNumericValue(c2)).toCharArray()); }

	public static boolean isZero(char[] in) {
		if (in == null) { return(true); }
		for (int i=0; i<in.length; i++) {
			if (in[i] != '0') {
				return(false);
			}
		}
		return(true);
	}

	public static char[] shiftLeft(char[] in, int num) {
		char[] result;
		if (num < 0) { return(null); }
		if (num == 0) { return(Arrays.copyOf(in,in.length)); }
		result = new char[in.length+num];
		for (int i=0; i<in.length; i++) {
			result[i] = in[i];
		}
		for (int i=in.length; i<result.length; i++) {
			result[i] = '0';
		}
		return(result);
	}

	public static char[] add(char[] op1, char[] op2) {
		char[] zero = {'0'};
		/* Shortcuts */
		if ((op1 == null)||(op1.length < 1)) {
			if ((op2 == null)||(op2.length < 1)) { return(zero); }
			else { return(Arrays.copyOf(op2,op2.length)); }
		} else {
			if ((op2 == null)||(op2.length < 1)) { return(Arrays.copyOf(op1,op1.length)); }
		}
			
		int carry;
		int opLong;
		int opShort;
		char[] longop;
		char[] result;
		if (op1.length > op2.length) {
			longop = op1;
			result = new char[op1.length+1];
			Arrays.fill(result,0,op1.length,'0');
			for (int i=0; i<op2.length; i++) {
				result[result.length-1-i] = op2[op2.length-1-i];
			}
		}
		else {
			longop = op2;
			result = new char[op2.length+1];
			Arrays.fill(result,0,op2.length,'0');
			for (int i=0; i<op1.length; i++) {
				result[result.length-1-i] = op1[op1.length-1-i];
			}
		}
		//System.err.println("add(): longop="+Arrays.toString(longop));
		//System.err.println("add(): result="+Arrays.toString(result));

		carry = 0;
		for (int i=0; i<longop.length; i++) {
			char [] ires = addSingle(longop[longop.length-1-i],result[result.length-1-i],carry);
			result[result.length-1-i] = ires[1];
			if (ires[0] == '0') { carry = 0; } else { carry = 1; }
		}
		if (carry>0) { result[0] = '1'; }
		result = stripLeadingZeros(result);
		//System.err.println("add(): result="+Arrays.toString(result));
		return(result);
	}

	/* c1 + c2 + carry */
	public static char[] addSingle(char c1, char c2, int carry) {
		int t = Character.getNumericValue(c1)+Character.getNumericValue(c2)+carry;
		char[] ret = new char[2];
		if (t > 9) { t = t - 10; ret[0] = '1'; }
		else { ret[0] = '0'; }
		ret[1] = Character.forDigit(t,10);
		return(ret);
	}

	/* subtract op2 from op1 */
	public static char[] subtract(char[] op1, char op2[]) {
		char[] result;
		char[] lg;
		char[] sm;
		char[] t1;
		char[] one = {'1'};
		char[] zero = {'0'};
		op1 = stripLeadingZeros(op1);
		op2 = stripLeadingZeros(op2);
		lg = op1;
		sm = op2;
		t1 = new char[lg.length];
		for (int i=sm.length-1,j=lg.length-1; i>=0; i--,j--) {
			t1[j] = diffSingle('9',sm[i]);
		}
		for (int j=lg.length-sm.length-1; j>=0; j--) {
			t1[j] = '9';
		}
		//System.err.println("subtract(): t1="+Arrays.toString(t1));
		/* add that result to lg */
		t1 = add(t1,lg);
		/* Add '1' */
		result = add(t1,one);
		if (result.length > lg.length) {
			/* Drop the '1' at the top */
			result[0] = '0';
		}
		//System.err.println("subtract(): result="+Arrays.toString(result));
		//System.err.println("subtract(): op1-op2="+new String(op1)+"-"+new String(op2)+"="+new String(result));
		return(result);
	}

	public static char diffSingle(char c1, char c2) {
		int t = Character.getNumericValue(c1)-Character.getNumericValue(c2);
		if (t < 0) { t = t * -1; }
		return((Integer.toString(t)).charAt(0));

	}

	public static char[] stripLeadingZeros(char[] in) {
		char[] zero = {'0'};
		int i;
		if (in == null) { return(zero); }
		i = 0;
		while((i < in.length)&&(in[i] == '0')) { i++; }
		if (i == in.length) { return(zero); }
		return(Arrays.copyOfRange(in,i,in.length));

	}

	public static char[] padLeadingZeros(char[] in, int len) {
		if (len <= in.length) { return(Arrays.copyOf(in,in.length)); }
		char[] result = new char[len];
		for (int i=0; i<(len-in.length); i++) {
			result[i] = '0';
		}
		for (int i=len-in.length,j=0; i<len; i++,j++) {
			result[i] = in[j];
		}
		return(result);
	}

	public static boolean checkStringIsInt(String input) {
		if (input.length() < 1) { return(false); }
		for(int i=0; i<input.length(); i++) {
			switch(input.charAt(i)) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					break;
				default:
					return(false);
			}
		}
		return(true);
	}

}
