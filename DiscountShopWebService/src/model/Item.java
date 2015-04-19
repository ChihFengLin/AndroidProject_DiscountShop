package model;

public class Item {
	private String itemName;
	private float price;
	private String imageName;
	private String imageBase64;
	/*Constructor*/
	public Item() {
		super();
	}
	/**
	 * @param itemName
	 * @param price
	 * @param imageName
	 * @param imageBase64
	 */
	public Item(String itemName, float price, String imageName,
			String imageBase64) {
		super();
		this.itemName = itemName;
		this.price = price;
		this.imageName = imageName;
		this.imageBase64 = imageBase64;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	
	
}
