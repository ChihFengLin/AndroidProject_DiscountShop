package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.DisplayItemDetail;



public class goToItemDetailDisplay implements ClickInterface {
    private Context mContext;
    public goToItemDetailDisplay(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, DisplayItemDetail.class);
        mContext.startActivity(main);
    }
}
