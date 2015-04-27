package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.RetailerLogin;


public class GoToRet implements ClickInterface {
    private Context mContext;
    public GoToRet(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, RetailerLogin.class);
        mContext.startActivity(main);
    }
}
