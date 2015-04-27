package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Item;
//for future usage and just implemented as local functionality
// because of the the image processing

public class AddItem {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private String sql = null;

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
