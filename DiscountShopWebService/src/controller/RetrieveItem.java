package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Item;
import model.ItemList;

public class RetrieveItem {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private String sql = null;

	public ItemList getItem(String retailerTag) {
		ItemList itemList= new ItemList();
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			// items
				sql = "Select * from items where retailer_tag = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, retailerTag);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Item item= new Item();
				item.setRetailerTag(rs.getString("retailer_tag").trim());
				item.setItemName(rs.getString("item_name").trim());
				item.setItemPrice(rs.getFloat("price"));
				item.setImage(rs.getString("image").trim());
				itemList.insertItem(item);
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

		return itemList;
		
	}
}
