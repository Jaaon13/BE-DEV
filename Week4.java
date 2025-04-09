import java.util.*;
import java.util.Map.Entry;

public class Week4 {
	
	// 3.
	public static String shortS(List<String> y) {
		int lSmall = y.get(0).length();
		int pos = 0;
		
		for(int x = 1; x < y.size(); x++) {
			int z = y.get(x).length();
			
			if(z < lSmall) {
				lSmall = z;
				pos = x;
			}
			
		}
		
		return y.get(pos);
	}
	
	//4.
	public static List<String> flip(List<String> y) {
		String temp1 = y.get(y.size() - 1);
		String temp2 = y.get(0);
		
		List<String> z = y;
		
		z.remove(0);
		z.add(0, temp1);
		
		z.remove(y.size() - 1);
		z.add(temp2);
		
		return z;
	}
	
	//5.
	public static String comb(List<String> y) {
		String z = "";
		
		for(int x = 0; x < y.size(); x++) {
			z += (y.get(x));
		}
		
		return z;
	}
	
	//6.
	public static List<String> search(List<String> y, String x) {
		List<String> w = new ArrayList<String>();
		
		for(int z = 0; z < y.size(); z++) {
			if(y.get(z).contains(x)) {
				w.add(y.get(z));
			}
		}
		
		return w;
	}
	
	//7.
	public static List<List<Integer>> list4(List<Integer> y) {
		List<Integer> a = new ArrayList<Integer>();
		List<Integer> b = new ArrayList<Integer>();
		List<Integer> c = new ArrayList<Integer>();
		List<Integer> d = new ArrayList<Integer>();
		
		List<List<Integer>> e = new ArrayList<List<Integer>>();
		e.add(a);
		e.add(b);
		e.add(c);
		e.add(d);
		
		for(int x = 0; x < y.size(); x++) {
			if(y.get(x) % 2 == 0) {
				a.add(y.get(x));
			}
		}
		
		for(int x = 0; x < y.size(); x++) {
			if(y.get(x) % 3 == 0) {
				b.add(y.get(x));
			}
		}
		
		for(int x = 0; x < y.size(); x++) {
			if(y.get(x) % 5 == 0) {
				c.add(y.get(x));
			}
		}
		
		for(int x = 0; x < y.size(); x++) {
			if(
				y.get(x) % 2 != 0
				&& y.get(x) % 3 != 0
				&& y.get(x) % 5 != 0
			  ) 
			{
				d.add(y.get(x));
			}
		}
		
		return e;
	}
	
	//8.
	public static List<Integer> lString(List<String> y) {
			List<Integer> z = new ArrayList<Integer>();
			
			for(int x = 0; x < y.size(); x++) {
				z.add( y.get(x).length() );
			}
			
			return z;
	}
	
	//10.
	public static HashSet<String> contains(HashSet<String> y, String d) {
		HashSet<String> a = new HashSet<String>();
		
		String[] c = (String[]) y.toArray(new String[0]);
		
		for(int s = 0; s < y.size(); s++) {
			if(c[s].toLowerCase().contains(d)) {
				a.add(c[s]);
			}
		}
		
		return a;
	}
	
	//11.
	public static List<String> toList(HashSet<String> y) {
		List<String> a = new ArrayList<String>();
		
		String[] b = (String[]) y.toArray(new String[0]);
		
		for(int x = 0; x < b.length; x++) {
			a.add(b[x]);
		}
		
		return a;
	}
	
	//12.
	public static HashSet<Integer> toEven(HashSet<Integer> y) {
		HashSet<Integer> a = new HashSet<Integer>();
		
		Integer[] b = (Integer[]) y.toArray(new Integer[0]);
		
		for(int x = 0; x < b.length; x++) {
			if(b[x] % 2 == 0) {
				a.add(b[x]);
			}
		}
		
		return a;
	}
	
	//14.
	public static String map(Map<String, String> y, String x) {
		
		if(y.containsValue(x)) {
			Set<String> a = y.keySet();
			String[] b = (String[]) a.toArray(new String[0]);
			for(int z = 0; z < b.length; z++) {
				String f = y.get(b[z]);
				if(f == x) {
					String c = b[z];
					return c;
				}
			}
		}
		return "Invalid";
	}
	
	//15.
	public static Map<Character, Integer> num15(List<String> y) {
		Map<Character, Integer> a = new HashMap<>();
		
		char[] c = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
				    'n','o','p','q','r','s','t','u','v','w','x','y','z'};
		int[] z = new int[26];
		for(int x = 0; x < 26; x++) {
			z[x] = 0;
		}
		
		String[] d = (String[]) y.toArray(new String[0]);
		
		for(int x = 0; x < d.length; x++) {
			for(int s = 0; s < 26; s++) {
				char[] t = d[x].toCharArray();
				if(t[0] == c[s]) {
					z[s]++;
				}
			}
		}
		
		for(int x = 0; x < 26; x++) {
			if(z[x] != 0) {
				a.put(c[x], z[x]);
			}
		}
		
		return a;
	}

	public static void main(String[] args) {
		// 1. Why would we use a StringBuilder instead of a String?
				// 		a. Instantiate a new StringBuilder
				//		b. Append the characters 0 through 9 to it separated by dashes
				// 				Note:  make sure no dash appears at the end of the StringBuilder
				
				StringBuilder a = new StringBuilder();
				char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
				
				for(int x = 0; x < b.length; x++) {
					a.append(b[x]);
					if(x != b.length - 1) {
						a.append('-');
					}
				}
				
				System.out.println("1. " + a);
				
				// 2. List of String:
				//		a. Create a list of Strings 
				//		b. Add 5 Strings to it, each with a different length

				List<String> c = new ArrayList<String>();
				c.add("1aaa");
				c.add("2bbbbbb");
				c.add("3ccccc");
				c.add("4dd");
				c.add("5eeee");
				
				System.out.println("2. " + c);
				
				// 3. Write and test a method that takes a list of strings 
				//			and returns the shortest string
				
				System.out.println("3. " + shortS(c) );
				
				// 4. Write and test a method that takes a list of strings 
				//			and returns the list with the first and last element switched
				
				System.out.println("4. " + flip(c));
				
				// 5. Write and test a method that takes a list of strings 
				//			and returns a string with all the list elements concatenated to each other,
				// 			separated by a comma
				
				List<String> d = new ArrayList<String>();
				d.add("apple");
				d.add("apples");
				d.add("apologetic");
				d.add("banana");
				d.add("burt");
				
				System.out.println("5. " + comb(d));
				
				// 6. Write and test a method that takes a list of strings and a string 
				//			and returns a new list with all strings from the original list
				// 			containing the second string parameter (i.e. like a search method)
				
				String e = "ap";
				
				System.out.println("6. " + search(d, e));
				
				
				
				// 7. Write and test a method that takes a list of integers 
				//			and returns a List<List<Integer>> with the following conditions specified
				//			for the return value:
				//		a. The first List in the returned value contains any number from the input list 
				//			that is divisible by 2
				//		b. The second List contains values from the input list that are divisible by 3
				//		c. The third containing values divisible by 5, and 
				//		d. The fourth all numbers from the input List not divisible by 2, 3, or 5

				List<Integer> f = new ArrayList<Integer>();
				
				for(int x = 1; x < 42; x++) {
					f.add(x);
				}
				
				List<List<Integer>> g = list4(f);
				
				System.out.println("7.");
				
				for(int x = 0; x < g.size(); x++) {
					System.out.println(g.get(x));
				}
				
				// 8. Write and test a method that takes a list of strings 
				//			and returns a list of integers that contains the length of each string

				System.out.println("8. " + lString(d));
				
				// 9. Create a set of strings and add 5 values
				
				HashSet<String> h = new HashSet<String>();
				h.add("Turtle");
				h.add("April");
				h.add("Zoo");
				h.add("Generational");
				h.add("Unoccupied");
				
				System.out.println("9. " + h);
				
				// 10. Write and test a method that takes a set of strings and a character 
				//			and returns a set of strings consisting of all the strings in the
				// 			input set that start with the character parameter.

				System.out.println("10. " + contains(h, "o"));
				
				// 11. Write and test a method that takes a set of strings 
				//			and returns a list of the same strings
			
				System.out.println("11. " + toList(h));

				// 12. Write and test a method that takes a set of integers 
				//			and returns a new set of integers containing only even numbers 
				//			from the original set

				HashSet<Integer> j = new HashSet<Integer>();
				for(int x = 0; x < 41; x++) {
					j.add(x);
				}
				
				System.out.println("12. " + toEven(j));
				
				
				// 13. Create a map of string and string and add 3 items to it where the key of each
				// 			is a word and the value is the definition of the word

				Map<String, String> k = new HashMap<String, String>();
				k.put("Apple", "Fruit");
				k.put("New York", "Place");
				k.put("Table", "Thing");
				
				System.out.println("13. " + k);
				
				// 14. Write and test a method that takes a Map<String, String> and a string 
				// 			and returns the value for a key in the map that matches the
				// 			string parameter (i.e. like a language dictionary lookup)

				System.out.println("14. " + map(k, "Fruit"));
				
				// 15. Write and test a method that takes a List<String> 
				//			and returns a Map<Character, Integer> containing a count of 
				//			all the strings that start with a given character
				
				List<String> l = new ArrayList<String>();
				l.add("a");l.add("a");l.add("a");l.add("z");l.add("s");l.add("y");l.add("xasd");l.add("asd");l.add("a");
				l.add("b");l.add("m");l.add("u");l.add("x");l.add("f");l.add("v");l.add("u");l.add("d");l.add("d");
				l.add("n");l.add("o");l.add("p");l.add("q");l.add("f");l.add("x");l.add("w");l.add("l");l.add("s");
				l.add("d");l.add("f");l.add("a");l.add("i");l.add("n");l.add("h");l.add("y");l.add("b");l.add("i");
				
				
				System.out.println("15. " + num15(l));
				
	}

}
