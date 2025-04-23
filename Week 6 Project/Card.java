
public class Card {
	
	private String Name;
	private String Suit;
	private int Value;
	
	
	public Card(String n, String s, int v) {
		this.Name = n;
		this.Suit = s;
		this.Value = v;
	}



	public void describe() {
		
		System.out.println("Name: " + Name + "\tSuit: " + Suit + "\tValue: " + Value);
		
	}
	
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getSuit() {
		return Suit;
	}
	public void setSuit(String suit) {
		Suit = suit;
	}
	
	public int getValue() {
		return Value;
	}
	public void setValue(int value) {
		Value = value;
	}
	
	
}
