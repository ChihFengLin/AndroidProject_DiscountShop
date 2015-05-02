package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class UpdateItem {
	private Connection connection = null;
	private Statement statement = null;
	private String sql = null;

	public boolean update(String retailerName, String itemName,
			String newItemName, String newItemPrice) {
		float newitemprice = Float.parseFloat(newItemPrice);
		sql = "UPDATE items SET item_name ='" + newItemName
				+ "' , price = " + newitemprice + " WHERE item_name ='"
				+ itemName + "' and retailer_tag ='" + retailerName + "';";
		return databaseUtil(sql);

	}

	public boolean delete(String retailerName, String itemName) {
		String sql = "DELETE FROM items WHERE item_name='" + itemName
				+ "' and retailer_tag='" + retailerName + "';";

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
