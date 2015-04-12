package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import model.Item;
//for future usage and just implemented as local functionality
// because of the the image processing

public class AddItem {
	private Connection connection = null;
	private Statement statement = null;
	private String sql = null;

	/* Write retailer information into database */
	public void addItem(Item item) throws SQLException {

		if (item == null) {
			System.out.println("Added item is empty!");
			return;
		}

		try {

			String sql = "INSERT INTO items (item_name, price, image) VALUES (?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, item.getItemName());
			stmt.setFloat(2, item.getPrice());

			/* Read image file path in the server side */
			File image = new File(item.getImageFilePath());
			FileInputStream fis = new FileInputStream(image);
			stmt.setBinaryStream(3, fis, (int) image.length());

			int count = stmt.executeUpdate();
			if (count > 0) {
				System.out.println("Insert Successfully");
			} else {
				System.out.println("Inset Fail");
			}

			fis.close();

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		connection.close();

	}
}
