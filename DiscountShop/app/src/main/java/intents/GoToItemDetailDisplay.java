package intents;

import android.content.Context;
import android.content.Intent;

import model.Item;
import product_exp.view.ConsumerDisplayItemDetail;



public class GoToItemDetailDisplay implements ClickInterface {
    private Context mContext;
    public GoToItemDetailDisplay(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        Item item = (Item)inputOne;
        String address = (String)inputTwo;

        Intent main = new Intent();
        main.setClass(packageContext, ConsumerDisplayItemDetail.class);
        main.putExtra("retailer name", item.getRetailerTag());
        main.putExtra("item name", item.getItemName());
        main.putExtra("item price", Float.toString(item.getItemPrice()));
        main.putExtra("picture", item.getImage());
        main.putExtra("latitude", item.getLatitude());
        main.putExtra("longitude", item.getLongitude());

        main.putExtra("address", address);

        mContext.startActivity(main);
    }
}
