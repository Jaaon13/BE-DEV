package projects.exception;

@SuppressWarnings("serial")
public class DbE extends RuntimeException {

	public DbE(String m) { super(m); }
	
	public DbE(Throwable c) { super(c); }
	
	public DbE(String m, Throwable c) { super(m, c); }
	
}
