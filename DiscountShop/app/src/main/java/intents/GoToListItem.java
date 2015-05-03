package intents;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import model.Login;
import product_exp.view.ConsumerItemListPage;
import product_exp.view.ConsumerLogin;
import product_exp.view.ConsumerRegister;
import product_exp.view.MainActivity;
import product_exp.view.RetailerItemListPage;




public class GoToListItem implements ClickInterface {
    Context mcontext;
    public GoToListItem(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo) {
        mcontext = packageContext;
        Intent main = new Intent();
        main.setClass(packageContext, ConsumerItemListPage.class);
        packageContext.startActivity(main);
    }

}
