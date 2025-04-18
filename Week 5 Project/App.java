
public class App {

	public static void main(String[] args) {
		
		Logger l = new AsteriskLogger();
		
		l.log("hello");
		
		l.error("hello");
		
		l = new SpacedLogger();

		l.log("hello");
		
		l.error("hello");
		
	}

}
