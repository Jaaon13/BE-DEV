import java.util.*;
/*
 * 
3. What are the differences between Lists, Sets, and Maps in Java?

Lists can hold any values including the same value

Sets can only hold unique values

And Maps hold two values, a key and a value.
The key can be used to call the value associated with it.
Also maps cannot hold to values for one key but can have multiple keys for one value

*/

public class Week4Research {

	public static void main(String[] args) {
			
		//5. Write Java code that does the following:

	    //(a) Write a line of code that shows how you would instantiate an ArrayList of String.
		
		List<String> a = new ArrayList<String>();
		// the string inside of the "<>" tells you what variables the list can and cannot accept
		
		a.add("Something 1");
		// the .add() method allows you to add data to the list as long as the types match

		a.add("Something 1");
		// can have multiple of the same value
		
	    //(b) Write a line of code that shows how you would instantiate a HashSet of StringBuilder.
		
		HashSet<StringBuilder> b = new HashSet<StringBuilder>();
		// Same assignment of variable type as list, only difference here is "HashSet"
		
		StringBuilder temp = new StringBuilder();
		temp.append("Something");
		temp.append(" ");
		temp.append("2");
		// initalizes and gives the Stringbuilder a value
		
		b.add(temp);
		// Same way to add a value to HashSet as a list
		
		b.add(temp);
		// cant have multiple of the same value
		
	    //(c) Write a line of code that shows how you would instantiate a HashMap of String, String.
		
		HashMap<String, String> c = new HashMap<String, String>();
		// Declared the same way as lists and sets, but needs to variable types, one for a key and one for a value
		
		c.put("New York", "Place");
		// Different way to add data to a map but main diffence is differnt method name and it requires 2 Variables
		
		c.put("New York", "Place");
		// Also cant have multiple
		
		c.put("New York", "	Home");
		// And can only have one value per key
		
		
		System.out.println("List: " + a);
		System.out.println("Set: " + b);
		System.out.println("Map: " + c);
	}

}
