import java.util.ArrayList;
import java.util.List;

public class War {
	
	static boolean wincon; // to stop the while loop if the game is won
	static boolean draw; // to see if the game is drawn bv using a try catch
	
	static int p1win = 0; // player one win
	static int p2win = 0; // player two win
	static int pdraw = 0; // player draw
	static int unbeatable = 0; // unbeatable games
	
	static int turn = 1;; // number of turns
	
	static Player P1 = new Player("p1");; // player one object
	static Player P2 = new Player("p2");; // player two object
	
	public static void Int() {
		
		// setting all the variables
		wincon = true;
		draw = false;
		
		p1win = 0;
		p2win = 0;
		pdraw = 0;
		unbeatable = 0;
		
		turn = 0;
		
		Deck t = new Deck(true);
		Deck p1 = new Deck(false);
		Deck p2 = new Deck(false);
		
		List<Card> p1a = new ArrayList<Card>();
		List<Card> p2a = new ArrayList<Card>();
		
		t.shuffle();
		
		for(int x = 0; x < 26; x++) { // deals 26 cards from the suffled 't' deck to player one
			p1a.add(t.draw());
		}
		for(int x = 0; x < 26; x++) { // deals 26 cards to player 2
			p2a.add(t.draw());
		}
		
		p1.setDeck(p1a);  // sets the temp player one deck to equal the list
		p2.setDeck(p2a);  // same for player two
		
		P1.setPdeck(p1); // sets the player object's deck to the temp deck
		P2.setPdeck(p2);
		
	}
	
	static List<Card> pot = new ArrayList<Card>(); // a place to hold the drawn cards
	
	public static void addPot(int i) { // 'i' is the player, 1 is equal to player one and 2 is player 2
		for(int x = 0; x < pot.size(); x++) {
			if(i == 1) { // player 1
				P1.getPdeck().add(pot.get(x)); // this loops through and adds the pot to the player deck
			} else { // Player 2
				P2.getPdeck().add(pot.get(x)); // same here
			}
		}
		
		pot.clear(); // this is to remove all the cards from the pot
	
	}
	
	public static void display(Card[] p) { // this method displays the player and their card and the current turn
		System.out.println("\nPlayer One card: ");
		p[0].describe();
		System.out.println("\nPlayer Two card: ");
		p[1].describe();
		System.out.println("Turn # " + turn);
	}
	
	public static Card[] draw() { // this draws two cards, one form each player
		Card[] c = new Card[2];   // and returns an array of the two cards
		c[0] = P1.getPdeck().draw();
		c[1] = P2.getPdeck().draw();
		
		return c;
	}
	
	public static void turn() {
		
		Card[] p = draw(); // draws two cards
		
		pot.add(p[0]); // adds them to the pot
		pot.add(p[1]);
		
		if(p[0].getValue() > p[1].getValue()) { // player one wins
			
			addPot(1);
			
		} else if(p[0].getValue() == p[1].getValue()) { // tie
			
			try { // this tries to rerun this method if it fails it catches and sets it to a draw
				turn(); // because one player doesn't have a card to draw
			}
			catch(Exception e) {
				draw = true;
			}
			
		} else { // player two wins
			
			addPot(2);
			
		}
		
		display(p);
		
	} 
	
	public static void win() { // this method checks if either players deck is greater than 51 which mean they win
		if(P1.getPdeck().getCards().size() > 51) {
			wincon = false;
			
			System.out.println("Player One Wins!");
			
			p1win++;
			
		} else if(P2.getPdeck().getCards().size() > 51) {
			wincon = false;

			System.out.println("Player Two Wins!");
			
			p2win++;
			
		} else if (draw == true) { // this checks if the catch went off and if it did it stops the game and sets it to a draw
			wincon = false;
			
			System.out.println("Draw!");
			
			pdraw++;
		}
		
		System.out.println("\nP1 cards: " + P1.getPdeck().getCards().size()
				+ " P2 cards: " + P2.getPdeck().getCards().size()); // prints out the player card totals for double checking
	}

	public static void start() {
		
		Int();
			
		while(wincon) {
			
			turn++;
			
			turn();
			
			win();
			
			if(turn > 20000) { // if turn ever gets this many turns its fair to end it in fear of a forever repeating game
				wincon = false;
				
				System.out.println("\nUnwinnable hand!\n");
				
				unbeatable++;
			}
			
		}
	}

}
