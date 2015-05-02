package controller;

public class Delete extends DatabaseUtil{
	public boolean deleteItem(String retailerName, String itemName) {
		sql = "DELETE FROM items WHERE item_name='" + itemName
				+ "' and retailer_tag='" + retailerName + "';";
		return databaseUtil(sql);
	}
}
