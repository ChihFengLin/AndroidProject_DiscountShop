package model;

public class Item {
    private String itemName;
    private float itemPrice;
    private String image;
    /*Constructor*/
    public Item() {
        super();
    }

    public Item(String itemName, float itemPrice, String image){
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.image=image;
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
