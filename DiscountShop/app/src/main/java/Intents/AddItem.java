package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.RetailerAddItem;
import product_exp.view.RetailerItemListPage;

/**
 * Created by Ravi on 4/11/2015.
 */
public class AddItem implements ClickInterface {
    private Context mContext;
    public AddItem(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo ){
        mContext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, RetailerItemListPage.class);
        if (packageContext.getClass().equals(RetailerAddItem.class)) {
            main.putExtra("Add Item", true);
        }
        mContext.startActivity(main);
    }
}
