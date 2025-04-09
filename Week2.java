	public static void greetingNoR(String str) {
    		System.out.println("This is: " + str);
    }

	
	public static String greetingR(String str) {
			return ("This is: " + str);
	}
	
	
	public static boolean compare(String str, int x) {
		return (str.length() > x);
	}
	
	
	public static int smallNum(int[] y) {
		int small = y[0];
		for(int z = 1; z <= (y.length -1); z++) {
			if(y[z] < small) {
				small = y[z];
			}
		}
		
		return small;
	}
	
	
	public static double doubAv(double[] y) {
		double sum = 0.0;
		for(int z = 0; z <= (y.length - 1); z++) {
			sum += y[z];
		}
		sum /= y.length;
		return sum;
	}
	
	
	public static int[] stringLet(String[] y) {
		int[] x = new int[y.length];
		for(int z = 0; z <= (y.length - 1); z++) {
			x[z] = y[z].length();
		}
		return x;
	}
	
	
	public static boolean evenOdd(String[] y) {
		int a = 0;
		int b = 0;
		
		for(int c = 0; c <= (y.length - 1); c++) {
			if(y[c].length() % 2 == 0) {
				a++;
			} else {
				b++;
			}
		}
				
		return (a > b);
	}
	
	public static boolean palindrome(String y) {
		y = y.toLowerCase();
		char[] x = y.toCharArray();
		int z = 0;
		for(int c = 0; c <= (y.length() - 1); c++) {
			if(x[c] == x[(y.length() - 1) - c]) {
				z++;
			}
		}
		return (z == y.length());
	}
	
	
    public static void main() {
        
    	int[] i = {1, 5, 2, 8, 13, 6};
    	
    	System.out.println(i[0]);
    	
    	System.out.println(i[4+1]);
    	
    	for(int x = 0; x <= (i.length - 1); x++) {
    		System.out.println(i[x]);
    	}
    	
    	for(int x = 0; x <= (i.length - 1); x++) {
    		if(i[x] % 2 != 0) {
    			System.out.println(i[x]);
    		}
    	}
    	
    	int sum = 0;
    	for(int x = 0; x <= (i.length - 1); x++) {
    		sum += i[x];
    	}
    	
    	double average = sum/i.length;
    	System.out.println(average);
    	
    	String[] names = {"Sam", "Sally", "Thomas", "Robert"};
    	
    	int lsum = 0;
    	
    	for(int x = 0; x <= (names.length - 1); x++) {
    		lsum += names[x].length();
    	}
    	System.out.println(lsum);
    	
    	greetingNoR("John Smith");
    	
    	System.out.println(greetingR("Jane Doe"));
    	
    	System.out.println(compare("apple", 3));
    	
    	int[] b = {123, 15, 5123, 3, 12313, 21};
    	System.out.println(smallNum(b));
    	
    	double[] c = {12.12, 124.1234, 7.0, 16.43};
    	System.out.println(doubAv(c));
    	
    	String[] d = {"a", "aa", "aaaa", "aaa", "asasaas"};
    	int[] e = stringLet(d);
    	for(int aa = 0; aa <= (e.length - 1); aa++) {
    		System.out.println(e[aa]);
    	}
    	
    	String[] f = {"aba", "aba", "abab"};
    	System.out.println(evenOdd(f));
    	
    	String g = "RaceCar";
    	System.out.println(palindrome(g));
    	
    }