package controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import databaseInitialization.MySqlConnection;
import model.Consumer;
import model.Item;
import model.Retailer;
//...............
//for future usage
//...............
// and just implemented as local functionality
//because of the the image processing
public class DeleteTable extends MySqlConnection{
	
	public void deleteConsumer(Consumer consumer) throws SQLException, IOException {
		
		Statement statement = null;
		Connection connect = null;
		connect = getConnection();
		
		statement = connect.createStatement();
		String sql= "DELETE FROM consumers WHERE username ='" + consumer.getUsername() + "';";
		statement.executeUpdate(sql);
		
		System.out.println("Delete consumer complete!\n");
		
		statement.close();
		connect.close();
	}
	
	public void deleteRetailer(Retailer retailer) throws SQLException, IOException {
		
		Statement statement = null;
		Connection connect = null;
		connect = getConnection();
		
		statement = connect.createStatement();
		String sql= "DELETE FROM retailers WHERE retailer_name ='" + retailer.getRetailerName() + "';";
		statement.executeUpdate(sql);
		
		System.out.println("Delete retailer complete!\n");
		
		statement.close();
		connect.close();
	}
	
	
	
	public void deleteItem(Item item) throws SQLException, IOException {
		
		Statement statement = null;
		Connection connect = null;
		connect = getConnection();
		
		statement = connect.createStatement();
		String sql= "DELETE FROM items WHERE item_name ='" + item.getItemName() + "';";
		statement.executeUpdate(sql);
		
		System.out.println("Delete item complete!\n");
		
		statement.close();
		connect.close();
	}
}
