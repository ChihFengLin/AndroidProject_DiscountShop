package databaseInitialization;

// this package is only used for the initialization, so it executes only one time

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
// this create
public class CreateTable extends MySqlConnection{
	
	/*Constructor*/
	public void initTable() throws SQLException {
		
		System.out.println("Start creating table in database");
		Connection conn = null;
		Statement statement = null;
		String INITURL = "jdbc:mysql://localhost:3306/";
		
		try {
			Class.forName(DRIVER);
			conn = getConnection(INITURL);
			statement = conn.createStatement();
			
			ClassLoader classLoader = getClass().getClassLoader();
			FileReader file = new FileReader(classLoader.getResource("schema.txt").getFile());
			
			//FileReader file = new FileReader("schema.txt");					
			BufferedReader buff = new BufferedReader(file);
			
			boolean eof = false;
			StringBuffer stringBuff = new StringBuffer();
			
			 do {
				 String line = buff.readLine();
	             if(line == null){
	            	 eof = true;
	                 break;
	             }
	             
	             stringBuff.append(line); 
	             int stringBuffSize = stringBuff.length();	
	             
	             if (stringBuff.charAt(stringBuffSize-1) == ';') {
	                statement.executeUpdate(stringBuff.toString());
	                /*Reset string buffer*/
	                stringBuff = new StringBuffer();    
	             }    
	         } while(!eof);
			 
	         buff.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Complete create table!\n");
		statement.close();
		conn.close();
		
	}

}
