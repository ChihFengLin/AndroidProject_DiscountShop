package intents;

import android.content.Context;
import android.content.Intent;
import product_exp.view.RetailerUpdateItem;


public class goToRetailerUpdate implements ClickInterface {
    private Context mContext;
    public goToRetailerUpdate(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, RetailerUpdateItem.class);
        mContext.startActivity(main);
    }
}
