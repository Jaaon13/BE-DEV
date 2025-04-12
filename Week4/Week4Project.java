
public class Week4Project {
	
	//7.
	public static String concat(String y, int z) {
		String temp = ""; // a holder string
		
		for(int x = 0; x < z; x++) {
			temp += y; // adds the string to temp z times
		}
		
		return temp;
	}
	
	//8.
	public static String fullName(String y, String z) {
		String temp = ""; // holder string
		
		temp = y + " " + z; // adds y and z together with a space in between
		
		return temp;
	}
	
	//9.
	public static boolean great(int[] y) {
		boolean temp = false;
		int temp2 = 0;
		
		for(int x = 0; x < y.length; x++) {
			temp2 += y[x]; // adds all of the values in y
		}
		
		if(temp2 > 100) {
			temp = true; // if bigger than 100 changes temp from false to true if not temp stays false
		}
		
		return temp;
	}
	
	//10.
	public static double av(double[] y) {
		double temp = 0.0;
		
		for(int x = 0; x < y.length; x++) {
			temp += y[x]; // adds all the values to temp
		}
		
		return temp / y.length; // returns the average by dividing the sum by the length of the array
	}
	
	//11.
	public static boolean av2(double[] y, double[] z) {
		boolean t = false;
		double temp = 0.0;
		double temp2 = 0.0;
		
		for(int x = 0; x < y.length; x++) {
			temp += y[x]; // adds sum of y to temp
		}
		
		for(int x = 0; x < z.length; x++) {
			temp2 += z[x]; // adds sum of z to temp2
		}
		
		temp /= y.length; // gets the average for both
		temp2 /= z.length;
		
		if(temp > temp2) { // sees if y average is larger than the z average
			t = true; // if it is it returns true otherwise returns false
		}
		
		return t;
	}
	
	//12.
	public static boolean willBuyDrink(boolean y, double z) {
		return ( y == true && z > 10.5 ); // checks if y is true and if z is greater then 10.5 then returns the outcome
	}
	
	//13.
	public static boolean onLine(double[] xy, double z, double[] a) { // xy is rise and run, z is x offset, a is the point
		// this is a method to check if a point is on a line
		boolean t = false;
		
		double t1 = xy[0] / xy[1]; // the equation for calculating if a point is on a line is y = rise/run * x + z
		
		double t2 = (t1 * a[0]) + z; // this solves the x side
		
		if(t2 == a[1]) { // this sees if the solved x side equals y if so the point is on the line
			t = true;
		}
		
		return t;
	}
	
	public static void main(String[] args) {
		
		//1.
		int[] ages = {3, 9, 23, 64, 2, 8, 28, 93};
		
		//1a.
		System.out.println("1a. " + ( ages[ages.length -1 ] - ages[0] ) );
		
		//1b.
		int[] ages2 = {3, 9, 23, 64, 2, 8, 28, 93, 182, 2};
		
		System.out.println("1b. " + ( ages2[ages2.length -1 ] - ages2[0] ) );
		
		//1c.
		int temp = 0;
		for(int x = 0; x < ages2.length; x++) { // loops through the length of the array
			temp += ages2[x]; // then adds each value to temp
		}
		
		temp /= ages2.length; // finally divides temp by the length of the array which gives the average
		
		System.out.println("1c. " + temp);
		
		//2.
		String[] names = {"Sam", "Tommy", "Tim", "Sally", "Buck", "Bob"};
		
		//2a.
		temp = 0; // reuses temp because it isnt need for question 1c anymore
		for(int x = 0; x < names.length; x++) { // loops through the entire array
			temp += names[x].length(); // adds the length of each string into temp
		}
		temp /= names.length; // gets the average
		
		System.out.println("2a. " + temp);
		
		//2b.
		String temp2 = "";
		for(int x = 0; x < names.length; x++) {
			temp2 += names[x]; // adds each string to temp2 to concatenate them
			if(x != names.length - 1) {
				temp2 += " "; // adds a space after each word if its not the last word
			}
		}
		
		System.out.println("2b. " + temp2);
		
		//3. & 4.
		/*
	
		You can access the last variable in an array by using, array[array.length - 1]
		
		To access the first variable in an array you always use array[0] as long as the array has data in it
		 
		 */
		
		//5.
		int[] nameLengths = new int[names.length]; // creates an array with the same size as the names array
		
		for(int x = 0; x < nameLengths.length; x++) {
			int temp3 = names[x].length();
			nameLengths[x] = temp3;
		}
		
		System.out.print("5. "); // couldnt get the array to print properly, unsure why, so i did it manually
		for(int x = 0; x < nameLengths.length; x++) {
			System.out.print(nameLengths[x]);
			if(x != 6) {
				System.out.print(" ");
			}
		}
		
		//6.
		int temp3 = 0;
		
		for(int x = 0; x < nameLengths.length; x++) {
			temp3 += nameLengths[x];
			System.out.print(""); // had issues with the code not executing after this loop so added this and now it works well
		}
		System.out.println();
		System.out.println("6. " + temp3);
		
		//7.
		System.out.println("7. " + concat("apple", 7));
		
		//8.
		System.out.println("8. " + fullName("John", "Doe"));
		
		//9.
		int[] a = {8, 12, 13, 54, 72};
		System.out.println("9. " + great(a));
		
		//10.
		double[] b = {17.2, 1.1, 19238.2, 0.4};
		System.out.println("10. " + av(b));
		
		//11.
		double[] c = {19.3, 123.4, 2.3, 1243.2};
		System.out.println("11. " + av2(b,c));
		
		//12.
		System.out.println("12. " + willBuyDrink(true, 10.2));
		
		//13.
		double[] rr = {1, 4};
		double offset = 3;
		double[] possiplePoint = {-16, -1}; // known correct line and point just to confirm the method works
		System.out.println("13a. " + onLine(rr, offset, possiplePoint));
		possiplePoint[0] = 13;
		possiplePoint[0] = 3; // this is to prove the method actually works
		System.out.println("13b. Wrong point example: " + onLine(rr, offset, possiplePoint));
		
	}

}
