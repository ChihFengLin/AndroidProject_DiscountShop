package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.RetailerAddItem;


public class GoToAddItem implements ClickInterface {
    public GoToAddItem(Context packageContext, Class<?> cl, Object one, Object two){
        Intent addITem = new Intent();
        String username = (String)two;
        addITem.setClass(packageContext, RetailerAddItem.class);
        addITem.putExtra("username",username);
        packageContext.startActivity(addITem);
    }
}

