package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.MainActivity;



public class GoToMain implements ClickInterface {
    private Context mcontext;
    public GoToMain(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mcontext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, MainActivity.class);
        packageContext.startActivity(main);
    }
}
