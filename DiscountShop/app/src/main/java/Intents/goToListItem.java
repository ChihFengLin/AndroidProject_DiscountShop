package intents;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import model.Login;
import product_exp.view.ConsumerLogin;
import product_exp.view.ConsumerRegister;
import product_exp.view.ItemListPage;
import product_exp.view.RetailerItemListPage;

/**
 * Created by Ravi on 4/11/2015.
 */
public class goToListItem implements ClickInterface {
    Context mcontext;
    public goToListItem(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mcontext = packageContext;
        Class<?> myclass = packageContext.getClass();
        String response = (String) inputTwo;
        JSONObject responseObj = null;
        if (myclass.equals(ConsumerLogin.class)) {
            Login loginUI = (Login) inputOne;
            try {
                //create JSON object from JSON string
                responseObj = new JSONObject(response);
                //get the success property
                boolean success = responseObj.getBoolean("success");
                if (success) {
                    Gson gson = new Gson();
                    //get the login information property
                    String loginInfo = responseObj.getString("loginInfo");
                    //create java object from the JSON object
                    Login login = gson.fromJson(loginInfo, Login.class);
                    if (login.getPassword().equals(loginUI.getPassword())) {
                        Intent goToItemList = new Intent();
                        goToItemList.setClass(packageContext, ItemListPage.class);
                        mcontext.startActivity(goToItemList);
                    } else {
                        Toast toast = Toast.makeText(packageContext, "Invalid password", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 105, 50);
                        toast.show();
                        //   errorMessage.setText();
                    }

                } else {
                    Toast toast = Toast.makeText(mcontext, "Username doesn't exist! Please register!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                    //  errorMessage.setText();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(myclass.equals(ConsumerRegister.class)){
            try {
                //create JSON object from JSON string
                responseObj = new JSONObject(response);
                //get the success property
                boolean success=responseObj.getBoolean("success");
                if(success){
                    Toast toast = Toast.makeText(mcontext, "Creating account is successful!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                    Intent goToItemList = new Intent();
                    if (packageContext.getClass().equals(ConsumerLogin.class)) {
                        goToItemList.setClass(mcontext, ItemListPage.class);
                    } else {
                        goToItemList.setClass(mcontext, RetailerItemListPage.class);
                    }
                    mcontext.startActivity(goToItemList);

                }else{
                    Toast toast = Toast.makeText(mcontext, "Creating account failure, maybe username does exist, Please try again!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                    //  errorMessage.setText();
                }


            }catch(JSONException e){
                e.printStackTrace();
            }

        }

    }

}
