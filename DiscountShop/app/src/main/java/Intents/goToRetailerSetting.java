package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.discountshop.RetailerItemListPage;
import product_exp.discountshop.RetailerSettings;

/**
 * Created by Ravi on 4/11/2015.
 */
public class goToRetailerSetting implements ClickInterface {
    private Context mContext;
    public goToRetailerSetting(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mContext = packageContext;
        String username = (String)inputTwo;
        Intent main = new Intent();
        main.setClass(packageContext, RetailerSettings.class);
        main.putExtra("username",username);
        mContext.startActivity(main);
    }
}
