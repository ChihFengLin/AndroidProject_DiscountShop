package product_exp.discountshop;

import android.widget.ImageView;
import android.widget.TextView;

public class TagView{
    ImageView image;
    TextView itemName, itemPrice, itemDistance;

    public TagView(ImageView image, TextView itemName, TextView itemPrice, TextView itemDistance){
        this.image = image;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDistance = itemDistance;

    }
}
