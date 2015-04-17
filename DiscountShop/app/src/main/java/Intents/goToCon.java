package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.ConsumerLogin;


public class goToCon implements ClickInterface {
    private Context mContext;
    public goToCon(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, ConsumerLogin.class);
        mContext.startActivity(main);
    }
}
