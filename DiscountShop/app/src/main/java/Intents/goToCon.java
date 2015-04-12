package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.ConsumerLogin;

/**
 * Created by Ravi on 4/11/2015.
 */
public class goToCon implements ClickInterface {
    private Context mContext;
    public goToCon(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, ConsumerLogin.class);
        mContext.startActivity(main);
    }
}
