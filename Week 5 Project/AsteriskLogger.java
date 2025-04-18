
public class AsteriskLogger implements Logger {

	@Override
	public void log(String log) {
		
		System.out.println("***" + log + "***");

	}

	@Override
	public void error(String error) {
		
		String t = "\n***" + error + "***";
		
		for(int x = 0; x < t.length()-1; x++) {
			System.out.print("*");
		}
		
		System.out.println(t);
		
		for(int x = 0; x < t.length()-1; x++) {
			System.out.print("*");
		}
		
	}

}
