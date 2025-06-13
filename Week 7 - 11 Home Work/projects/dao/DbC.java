package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import projects.exception.DbE;

public class DbC {
	
	private static String HOST = "localhost";
	private static String PASSWORD = "password";
	private static int PORT = 3306;
	private static String SCHEMA = "week7";
	private static String USER = "week7";
	
	public static Connection getConn() {
		
		String uri = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", HOST, PORT, SCHEMA, USER, PASSWORD);
		
		try {
			
			return DriverManager.getConnection(uri);
			
		} catch (SQLException e) {
			throw new DbE(e + "\nFailed to connect to " + SCHEMA);
		}
		
	}
	
}
