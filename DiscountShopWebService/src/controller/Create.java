package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Consumer;
import model.Item;
import model.Retailer;

public class Create extends DatabaseUtil{
	private PreparedStatement statement = null;	

	public boolean createConsumer(Consumer consumer) {
		// INSERT INTO consumers VALUES (email, username, password);
		StringBuffer stringBuff = new StringBuffer(
				"INSERT INTO consumers (email, username, password) VALUES (");
		stringBuff.append("'" + consumer.getEmail() + "'" + " ,");
		stringBuff.append("'" + consumer.getUsername() + "'" + " ,");
		stringBuff.append("ENCODE( '" + consumer.getPassword() + "'," + "'abcdefg'" + "));");
		sql = stringBuff.toString();
		return databaseUtil_create(sql);
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
		return databaseUtil_create(sql);
	}
	
	/* Write retailer information into database */
	public boolean insertItem(Item item)  {

		if (item == null) {
			System.out.println("Added item is empty!");
			return false;
		}

		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			sql = "INSERT INTO items (retailer_tag,item_name, price, image, latitude, longitude) VALUES (?,?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, item.getRetailerTag());
			statement.setString(2, item.getItemName());
			statement.setFloat(3, item.getItemPrice());
			statement.setString(4, item.getImage());
			statement.setDouble(5, item.getLatitude());
			statement.setDouble(6, item.getLongitude());
			
			int count = statement.executeUpdate();
			
			statement.close();
			statement=null;
			
			connection.close();
			connection=null;
			
			if (count > 0) {
				System.out.println("Insert Successfully");
				return true;
			} else {
				System.out.println("Insert Fail");
			}
			
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
