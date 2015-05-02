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
import model.ItemList;
import model.Location;
import model.Login;

public class Read {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private String sql = null;
	private List<Item> items = new ArrayList<Item>();
	private Item item;
	
	// get the itemList for the specific retailer
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
				item.setLatitude(rs.getDouble("latitude"));
				item.setLongitude(rs.getDouble("longitude"));
				
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
	// get the location based on the retailerTag
	public Location getLocation(String retailerTag) {
		// itemList= new ItemList();
		Location location= null;
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			// items
				sql = "Select * from retailers where username = ?";
			
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
	
	// get the login information of consumer and retailer
	public Login getInfo(String loginType, String username) {
		Login login = new Login();

		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			// consumer
			if (loginType.equals("consumer")) {
				sql = "Select DECODE(password, 'abcdefg') from consumers where username = ?";
				
			}
			// retailer
			else {
				sql = "Select DECODE(password, 'hijklmn') from retailers where username = ?";
			}
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();

			login.setUsername(username.trim());
			
			
			if(loginType.equals("consumer")) {
				while (rs.next()) {
					login.setPassword(rs.getString("DECODE(password, 'abcdefg')").trim());
				}
			} else {
				while (rs.next()) {
					login.setPassword(rs.getString("DECODE(password, 'hijklmn')").trim());
				}	
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

		return login;
	}
// get the item list from the keyword
public List<Item> getSearchItemList(String itemName) {
		
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			
			if(itemName.equals("all")) {
				sql = "Select * from items";
			} else {
				sql = "Select * from items where item_name LIKE '%" + itemName + "%'";
			}
			
			statement = connection.prepareStatement(sql);
			//statement.setString(1, itemName);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				item = new Item();
				item.setRetailerTag(rs.getString("retailer_tag").trim());
				item.setItemName(rs.getString("item_name").trim());
				item.setItemPrice(rs.getFloat("price"));
				item.setImage(rs.getString("image").trim());
				item.setLatitude(rs.getDouble("latitude"));
				item.setLongitude(rs.getDouble("longitude"));
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

// get the all items from database
public List<Item> getWholeItemList() {
	
	try {
		Context ctx = (Context) new InitialContext()
				.lookup("java:comp/env");
		connection = ((DataSource) ctx.lookup("jdbc/mysql"))
				.getConnection();
		
		sql = "Select * from items";
		statement = connection.prepareStatement(sql);
		//statement.setString(1, itemName);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			item = new Item();
			item.setRetailerTag(rs.getString("retailer_tag").trim());
			item.setItemName(rs.getString("item_name").trim());
			item.setItemPrice(rs.getFloat("price"));
			item.setImage(rs.getString("image").trim());
			item.setLatitude(rs.getDouble("latitude"));
			item.setLongitude(rs.getDouble("longitude"));
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
