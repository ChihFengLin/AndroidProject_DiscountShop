package model;

public class Item {
	private String retailerTag;
	private String itemName;
	private float itemPrice;
	private String image;
	/*Constructor*/
	public Item() {
		super();
	}
	
	public Item(String retailerTag, String itemName, float itemPrice, String image){
		this.retailerTag=retailerTag;
		this.itemName=itemName;
		this.itemPrice=itemPrice;
		this.image=image;
	}
	
	public String getRetailerTag() {
		return retailerTag;
	}

	public void setRetailerTag(String retailerTag) {
		this.retailerTag = retailerTag;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
