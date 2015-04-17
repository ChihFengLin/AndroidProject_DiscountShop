package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.RetailerLogin;


public class goToRet implements ClickInterface {
    private Context mContext;
    public goToRet(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, RetailerLogin.class);
        mContext.startActivity(main);
    }
}
