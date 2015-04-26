package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Item;

public class ReturnSearchItem {
	
	private Connection connection = null;
	private PreparedStatement statement = null;
	private String sql = null;
	private List<Item> items = new ArrayList<Item>();
	private Item item;

	public List<Item> getSearchItemList(String itemName) {
		
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			
			sql = "Select * from items where item_name LIKE '%" + itemName + "%'";
			statement = connection.prepareStatement(sql);
			//statement.setString(1, itemName);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				item = new Item();
				item.setRetailerTag(rs.getString("retailer_tag").trim());
				item.setItemName(rs.getString("item_name").trim());
				item.setItemPrice(rs.getFloat("price"));
				item.setImage(rs.getString("image").trim());
				items.add(item);
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

		return items;
	}

}
