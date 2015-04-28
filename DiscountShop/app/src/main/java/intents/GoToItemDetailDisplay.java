package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.ConsumerDisplayItemDetail;



public class GoToItemDetailDisplay implements ClickInterface {
    private Context mContext;
    public GoToItemDetailDisplay(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        String itemName = (String)inputOne;
        String address = (String)inputTwo;

        Intent main = new Intent();
        main.setClass(packageContext, ConsumerDisplayItemDetail.class);
        main.putExtra("item name", itemName);
        //main.putExtra("item price", price);
        main.putExtra("address", address);

        mContext.startActivity(main);
    }
}
