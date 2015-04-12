package intents;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import model.Login;
import product_exp.view.ConsumerLogin;
import webservice.JSONRequest;
import webservice.NetworkStatus;

/**
 * Created by Ravi on 4/11/2015.
 */
public class LoginReq implements ClickInterface {
    private final String process_response_filter="action.getConsumerLoginInfo";
    private Context mContext;
    public LoginReq(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        Login login = (Login)inputOne;
        String username=login.getUsername();
        String password=login.getPassword();
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
                msgIntent.putExtra(JSONRequest.IN_MSG,"getLoginInfo");
                msgIntent.putExtra("username",username.trim());
                if (packageContext.getClass().equals(ConsumerLogin.class)) {
                    msgIntent.putExtra("loginType", "consumer");
                } else {
                    msgIntent.putExtra("loginType", "retailer");
                }
                msgIntent.putExtra("processType",process_response_filter);
                mContext.startService(msgIntent);
            }
        }

    }
}
