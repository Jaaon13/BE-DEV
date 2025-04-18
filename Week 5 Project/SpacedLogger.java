
public class SpacedLogger implements Logger {

	@Override
	public void log(String log) {
		char[] t = log.toCharArray();
		
		System.out.println();
		
		for(int x = 0; x < t.length; x++) {
			System.out.print(t[x] + " ");
		}

	}

	@Override
	public void error(String error) {
		char[] t = error.toCharArray();
		
		System.out.print("\nERROR: ");
		
		for(int x = 0; x < t.length; x++) {
			System.out.print(t[x] + " ");
		}

	}

}
