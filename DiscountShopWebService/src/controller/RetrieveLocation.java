package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Location;

public class RetrieveLocation {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private String sql = null;

	public Location getLocation(String retailerTag) {
		// itemList= new ItemList();
		Location location= null;
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			// items
				sql = "Select * from retailers where retailer_name = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, retailerTag);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				location=new Location();
				location.setLat(rs.getDouble("latitude"));
				location.setLng(rs.getDouble("longitude"));
			}

			rs.close();
			statement.close();
			statement = null;

			connection.close();
			connection = null;

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

		return location;
		
	}
}
