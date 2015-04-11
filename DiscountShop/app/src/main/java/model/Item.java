package model;


public class Item {
    private String itemName;
    private float price;
    private String imageFilePath;

    /*Constructor*/
    public Item() {
        super();
    }

    public Item(String ItemName, float Price, String ImageFilePath) {
        this.itemName = ItemName;
        this.price = Price;
        this.imageFilePath = ImageFilePath;
    }

    public void setItemName(String ItemName) {
        this.itemName = ItemName;
    }

    public void setPrice(float Price) {
        this.price = Price;
    }

    public void setImageFilePath(String ImageFilePath) {
        this.imageFilePath = ImageFilePath;
    }

    public String getItemName() {
        return this.itemName;
    }

    public float getPrice() {
        return this.price;
    }

    public String getImageFilePath() {
        return this.imageFilePath;
    }

}

