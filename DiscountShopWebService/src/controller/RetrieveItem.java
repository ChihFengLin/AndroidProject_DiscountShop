package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Item;

public class RetrieveItem {
	private Connection connection = null;
	private PreparedStatement statement = null;
	private String sql = null;

	public Item getItem(String imageName) {
		Item item= new Item();
		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			// consumer
				sql = "Select * from imageTest where imageName = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, imageName);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				item.setImageName(rs.getString("imageName").trim());
				item.setImageBase64(rs.getString("image").trim());
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

		return item;
		
	}
}
