package controller;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Location;


//already act as a web service
public class Update{
	private Connection connection = null;
	private Statement statement = null;
	private String sql = null;
	public boolean updateAddress(String username, String newAddress) {
		GeoParser geoParser = new GeoParser();
		Location location = geoParser.addressParser(newAddress);
		
		if (location != null) {
			System.out.println("debug: "+location.getLat());
			double latitude = location.getLat();
			double longitude = location.getLng();
			boolean flag = true;
			sql = "UPDATE retailers SET address ='" + newAddress
					+ "', latitude = " + latitude + ", longitude = "
					+ longitude + " WHERE username ='" + username + "';";
			flag &= databaseUtil(sql);
			
			sql = "UPDATE items SET latitude = " + latitude + ", longitude = "
					+ longitude + " WHERE retailer_tag ='" + username + "';";
			flag &= databaseUtil(sql);

			return flag;
			
		} 
		
		else {
			
			return false;
		}
	}
	
	public boolean updateItem(String retailerName, String itemName,
			String newItemName, String newItemPrice) {
		float newitemprice = Float.parseFloat(newItemPrice);
		sql = "UPDATE items SET item_name ='" + newItemName
				+ "' , price = " + newitemprice + " WHERE item_name ='"
				+ itemName + "' and retailer_tag ='" + retailerName + "';";
		return databaseUtil(sql);

	}
	
	private boolean databaseUtil(String sql) {
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();

			statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			statement.close();
			statement = null;

			connection.close();
			connection = null;

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
