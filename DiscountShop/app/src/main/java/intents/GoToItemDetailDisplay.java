package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.ConsumerDisplayItemDetail;



public class GoToItemDetailDisplay implements ClickInterface {
    private Context mContext;
    public GoToItemDetailDisplay(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, ConsumerDisplayItemDetail.class);
        mContext.startActivity(main);
    }
}
