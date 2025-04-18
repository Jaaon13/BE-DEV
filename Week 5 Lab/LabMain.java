import java.util.*;

public class LabMain {
	
	static Scanner t = new Scanner(System.in);
	
	public static boolean display() {
			System.out.println("\nDisplay? ");
			System.out.println("1. Yes");
			System.out.println("2. No");
			int t2 = t.nextInt();
			System.out.println();
			
			if(t2 == 1) {
				return true;
			}
			else {
				return false;
			}
		}
	
	public static Map<String, List<Card>> startGame() {
		Map<String, List<Card>> players = new HashMap<>();
		
		System.out.println("5. Create Game");
		System.out.println("Enter num of players (Max of 8) :");
		
		Deck d = new Deck();
		
		int t2 = t.nextInt();
		
		if(t2 < 9 && t2 > 0) {
			int numofcards = 52/t2;
			for(int i = 0; i < t2; i++) {
				
				List<Card> t3 = new ArrayList<>();
			
				for(int x = 0; x < numofcards; x++) {
					t3.add(d.draw());
				}
				players.put( ( "p" + Integer.toString(i+1) ) , t3);
				
			}
		} else {
			System.out.println("\nError Invalid Players\n");
			startGame();
		}
		
		return players;
	}
	
	public static void printPlayers(Map<String, List<Card>> p) {
		
		for(int x = 0; x < p.size(); x++ ) {
			String z = "p" + Integer.toString(x+1);
			List<Card> t4 = p.get(z);
			
			if(t4.size() != 0) {
				System.out.println("Player: " + z + "\nCards (" + t4.size() + "): ");
				
				for(int y = 0; y < t4.size(); y++) {
					t4.get(y).describe();
				}
				
			}
			
		}
		
	}

	public static void main(String[] args) {

		//1.
		System.out.println("1. Card Class\n");
		
		Card c1 = new Card("Queen", "Spades", 10);
		
		c1.describe();
		
		System.out.println();
		
		//2.
		System.out.println("2. Deck Class");
		
		Deck d1 = new Deck();
		
		if(display()) {
			d1.describe();
		}
		
		//3.
		System.out.println("3. Deck Shuffle");
		
		d1.shuffle();
		
		if(display()) {
			d1.describe();
		}
		
		//4.
		System.out.println("4. Draw\n");
		
		Card t = d1.draw();
		
		t.describe();
		
		System.out.println();
		
		//5.
		printPlayers(startGame());
		
	}

}
