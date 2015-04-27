package intents;

import android.content.Context;
import android.content.Intent;
import product_exp.view.RetailerUpdateItem;


public class GoToRetailerUpdate implements ClickInterface {
    private Context mContext;
    public GoToRetailerUpdate(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, RetailerUpdateItem.class);
        mContext.startActivity(main);
    }
}
