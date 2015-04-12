package intents;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import model.Consumer;
import model.Login;
import model.Retailer;
import product_exp.discountshop.ConsumerRegister;
import product_exp.discountshop.RetailerRegister;
import webservice.JSONRequest;
import webservice.NetworkStatus;

/**
 * Created by Ravi on 4/11/2015.
 */
public class UserCreate implements ClickInterface {
    private final String process_response_filter="action.getConsumerLoginInfo";
    private Context mContext;
    public UserCreate(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        String username = null;
        String retailerName = null;
        String password = null;
        String email = null;
        String address = null;
        int zipCode = 0;
        mContext = packageContext;
        if (packageContext.getClass().equals(ConsumerRegister.class)) {

            Consumer consumer = (Consumer) inputOne;
             username = consumer.getUsername();
             password = consumer.getPassword();
             email = consumer.getEmail();
        } else if (packageContext.getClass().equals(RetailerRegister.class)){
            Retailer ret = (Retailer) inputOne;
             username = ret.getUsername();
             password = ret.getPassword();
             email = ret.getEmail();
             address = ret.getAddress();
             zipCode = ret.getZipCode();
             retailerName = ret.getRetailerName();
        }

        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(mContext);
        if(internet){
            //if not username was entered
            if (username.trim().isEmpty()||password.trim().isEmpty()){
                Toast toast = Toast.makeText(mContext, "Please enter your username and password!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }else{
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent= new Intent(mContext, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG,"createUser");
                msgIntent.putExtra("email",email);
                msgIntent.putExtra("username",username);
                msgIntent.putExtra("password",password);
                if (packageContext.getClass().equals(ConsumerRegister.class)) {
                    msgIntent.putExtra("userType", "consumer");
                } else if (packageContext.getClass().equals(RetailerRegister.class)){
                    msgIntent.putExtra("retailerName",retailerName);
                    msgIntent.putExtra("address",address);
                    msgIntent.putExtra("zipCode",zipCode);
                    msgIntent.putExtra("userType","retailer");
                }
                msgIntent.putExtra("processType",process_response_filter);
                mContext.startService(msgIntent);
            }
        }
    }
}
