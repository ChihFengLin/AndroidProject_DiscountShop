package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.RetailerAddItem;


public class GoToAddItem implements ClickInterface {
    public GoToAddItem(Context packageContext, Class<?> cl, Object one, Object two){
        Intent addITem = new Intent();
        addITem.setClass(packageContext, RetailerAddItem.class);
        packageContext.startActivity(addITem);
    }
}
