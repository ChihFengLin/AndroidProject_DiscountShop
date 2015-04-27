package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;




import org.apache.tomcat.util.security.MD5Encoder;

import model.Consumer;
import model.Retailer;
// already act as a web service
public class CreateUser {
	private Connection connection = null;
	private Statement statement = null;
	private String sql = null;

	public boolean createConsumer(Consumer consumer) {
		// INSERT INTO consumers VALUES (email, username, password);
		StringBuffer stringBuff = new StringBuffer(
				"INSERT INTO consumers (email, username, password) VALUES (");
		stringBuff.append("'" + consumer.getEmail() + "'" + " ,");
		stringBuff.append("'" + consumer.getUsername() + "'" + " ,");
		stringBuff.append("ENCODE( '" + consumer.getPassword() + "'," + "'abcdefg'" + "));");
		sql = stringBuff.toString();
		return databaseUtil(sql);
	}

	public boolean createRetailer(Retailer retailer) {
		// INSERT INTO retailers VALUES (email, username, password,
		// retailer_name, address, zip_code);
		StringBuffer stringBuff = new StringBuffer(
				"INSERT INTO retailers (email, username, password, retailer_name, address, zip_code, latitude, longitude) VALUES (");
		stringBuff.append("'" + retailer.getEmail() + "'" + " ,");
		stringBuff.append("'" + retailer.getUsername() + "'" + " ,");
		stringBuff.append("ENCODE( '" + retailer.getPassword() +"',"+ "'hijklmn'" + ") ,");
		stringBuff.append("'" + retailer.getRetailerName() + "'" + " ,");
		stringBuff.append("'" + retailer.getAddress() + "'" + " ,");
		stringBuff.append(retailer.getZipCode() + " ,");
		stringBuff.append(retailer.getLatitude() + " ,");
		stringBuff.append(retailer.getLongitude() + ");");
		sql = stringBuff.toString();
		return databaseUtil(sql);

	}

	private boolean databaseUtil(String sql) {
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();

			statement = connection.createStatement();
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet keys=statement.getGeneratedKeys();
			int id=-1;
			while(keys.next()){
				id=keys.getInt(1);
			}
			statement.close();
			statement = null;

			connection.close();
			connection = null;
			
			keys.close();
			
			if(id==-1)
			return false;	
			else
			return true;
			

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqlex) {
					// ignore -- as we can't do anything about it here
				}
				statement = null;
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqlex) {
					// ignore -- as we can't do anything about it here
				}
				connection = null;
			}
		}
		return false;
	}

}
