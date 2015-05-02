package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.RetailerUpdateDeleteItem;
import model.Item;

public class GoToRetailerUpdate implements ClickInterface {
    private Context mContext;
    public GoToRetailerUpdate(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        Item item = (Item)inputOne;

        Intent main = new Intent();
        main.setClass(packageContext, RetailerUpdateDeleteItem.class);
        main.putExtra("retailer name", item.getRetailerTag());
        main.putExtra("item name",item.getItemName());
        main.putExtra("item price", Float.toString(item.getItemPrice()));
        main.putExtra("picture",item.getImage());
        main.putExtra("latitude",item.getLatitude());
        main.putExtra("longitude",item.getLongitude());
        mContext.startActivity(main);
    }
}
