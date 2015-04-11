package entities;


public interface ItemInterface {

    public void setItemName(String ItemName);
    public void setPrice(float Price);
    public void setImageFilePath(String ImageFilePath);
    public String getItemName();
    public float getPrice();
    public String getImageFilePath();
}
