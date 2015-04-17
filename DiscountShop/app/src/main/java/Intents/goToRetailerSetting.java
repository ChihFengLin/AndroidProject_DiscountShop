package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.RetailerSettings;


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
