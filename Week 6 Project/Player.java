
public class Player {
	
	private String Name;
	private Deck Pdeck;
	
	Player(String t) {
		Name = t;
	}
	
	public void describe() {
		String t = "\nName: " + Name + " Cards:\n";
		System.out.println(t);
		Pdeck.describe();
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public Deck getPdeck() {
		return Pdeck;
	}
	
	public void setPdeck(Deck pdeck) {
		Pdeck = pdeck;
	}
	
	
	
	
}
