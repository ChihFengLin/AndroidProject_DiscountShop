package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Item;
import model.Location;
import model.Retailer;

//already act as a web service
public class Update {
	private Connection connection = null;
	private Statement statement = null;
	private String sql = null;

	public boolean updateAddress(String username, String newAddress) {
		GeoParser geoParser = new GeoParser();
		Location location = geoParser.addressParser(newAddress);

		if (location != null) {
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
		} else {
			return false;
		}

	}

	// for future usage just implemented as local functionality
	/* Update item's price of the retailer (Still not work) */
	public void updateItemPrice(Retailer retailer, Item item, float newPrice)
			throws SQLException, IOException {

		/* 1st: Search selected retailer from database */
		statement = connection.createStatement();
		String sql = "SELECT id FROM retailers WHERE retailer_name = '"
				+ retailer.getRetailerName() + "';";
		ResultSet keys = statement.executeQuery(sql);
		int retailer_id = 1;
		while (keys.next()) {
			retailer_id = keys.getInt("id");
		}

		/* 2nd: Search selected item from the database and update the price */
		sql = "UPDATE items SET price =" + newPrice + " WHERE retailer_id ="
				+ retailer_id + " AND item_name ='" + item.getItemName() + "';";
		statement.executeUpdate(sql);

		System.out.println("Update complete!\n");
		statement.close();
		connection.close();

	}

	private boolean databaseUtil(String sql) {
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();

			statement = connection.createStatement();
			statement.executeUpdate(sql);
			// ResultSet keys=statement.getGeneratedKeys();
			// int id=-1;
			// while(keys.next()){
			// id=keys.getInt(1);
			// }
			statement.close();
			statement = null;

			connection.close();
			connection = null;

			// keys.close();
			//
			// if(id==-1)
			// return false;
			// else
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
