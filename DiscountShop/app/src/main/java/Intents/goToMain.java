package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.MainActivity;

/**
 * Created by Ravi on 4/11/2015.
 */
public class goToMain implements ClickInterface {
    private Context mcontext;
    public goToMain(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mcontext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, MainActivity.class);
        packageContext.startActivity(main);
    }
}