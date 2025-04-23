import java.util.ArrayList;
import java.util.List;

public class Deck {
	
	private List<Card> cards = new ArrayList<Card>();
	
	private List<String> Names = new ArrayList<String>();
	private List<String> Suit = new ArrayList<String>();
	private List<Integer> Value = new ArrayList<Integer>();
	
	public Deck(boolean t1) {
		
		Int();
		
		if(t1) {
			for(int i = 0; i < 4; i++) {
				
				for(int x = 0; x < 13; x++) {
					Card t = new Card(Names.get(x), Suit.get(i), Value.get(x));
					cards.add(t);
				}
			
			}
		}
		
	}
	
	public void setDeck(List<Card> t) {
		cards = t;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public Card draw() {
		Card d = cards.get(cards.size()-1);
		cards.remove(cards.size()-1);
		return d;
	}
	
	public void add(Card c) {
		cards.add(c);
	}
	
	public void shuffle() {
		List<Card> sCards = new ArrayList<Card>();
		
		for(int x = 0; x < 52; x++) {
			int ran = (int)(Math.random() * (52 - x));
			sCards.add( cards.get(ran) );
			cards.remove(ran);
		}
		
		cards = sCards;
	}
	
	public void describe() {
		
		for(Card c : cards) {
			c.describe();
		}
		
	}
	
	private void Int() {
		
		Names.add("Ace");
		Names.add("Two");
		Names.add("Three");
		Names.add("Four");
		Names.add("Five");
		Names.add("Six");
		Names.add("Seven");
		Names.add("Eight");
		Names.add("Nine");
		Names.add("Ten");
		Names.add("Jack");
		Names.add("Queen");
		Names.add("King");
		
		Suit.add("Clubs");
		Suit.add("Diamonds");
		Suit.add("Hearts");
		Suit.add("Spades");
		
		for(int x = 1; x < 14; x++) {
			if(x < 2) {
				Value.add(14);
			}
			else {
				Value.add(x);
			}
		}
		
	}
	
}
