package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Login;
//already act as a web service
public class RetrieveLoginInfo {

	private Connection connection = null;
	private PreparedStatement statement = null;
	private String sql = null;

	public Login getInfo(String loginType, String username) {
		Login login = new Login();

		try {
			Context ctx = (Context) new InitialContext()
					.lookup("java:comp/env");
			connection = ((DataSource) ctx.lookup("jdbc/mysql"))
					.getConnection();
			// consumer
			if (loginType.equals("consumer")) {
				sql = "Select * from consumers where username = ?";
			}
			// retailer
			else {
				sql = "Select * from retailers where username = ?";
			}
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				login.setUsername(rs.getString("username").trim());
				login.setPassword(rs.getString("password").trim());
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

}
