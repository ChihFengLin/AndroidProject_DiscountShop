package product_exp.discountshop;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import webservice.JSONRequest;
import webservice.NetworkStatus;
import model.Login;


public class retailerLogin extends Activity {

    private BroadcastReceiver receiver;
    private EditText usernameText;
    private EditText passwordText;
    private String username;
    private String password;
    private final String process_response_filter="action.getRetailerLoginInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        usernameText=(EditText)findViewById(R.id.usernameinput);
        passwordText=(EditText)findViewById(R.id.passwordinput);

        // Register receiver so that this Activity can be notified
        // when the JSON response came back
        //set the receiver filter
        IntentFilter filter = new IntentFilter(process_response_filter);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        // implement the receiving details,
        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String response= null;
                String responseType=intent.getStringExtra(JSONRequest.IN_MSG);
                if(responseType.trim().equalsIgnoreCase("getLoginInfo")){
                    response=intent.getStringExtra(JSONRequest.OUT_MSG);
                    // switch to another activity is included
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver,filter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_retailer_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy(){
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void goRetailerItemList(View v) {
        askToGetLoginInfo();

    }

    public void goSignUp(View v) {
        Intent goToSignUp = new Intent();
        goToSignUp.setClass(this, RetailerRegister.class);
        startActivity(goToSignUp);
    }

    //sending...
    //ask to send JSON request
    private void askToGetLoginInfo(){
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            username=usernameText.getText().toString();
            password=passwordText.getText().toString();
            //if not username was entered
            if (username.trim().isEmpty()||password.trim().isEmpty()){
                Toast toast = Toast.makeText(this, "Please enter your username and password!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }else{
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent= new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG,"getLoginInfo");
                msgIntent.putExtra("username",username.trim());
                msgIntent.putExtra("loginType","retailer");
                msgIntent.putExtra("processType",process_response_filter);
                startService(msgIntent);
            }
        }
    }

    //receiving...
    //parse and display JSON response
    private void processJsonResponse(String response){
        JSONObject responseObj=null;
        try {
            //create JSON object from JSON string
            responseObj = new JSONObject(response);
            //get the success property
            boolean success=responseObj.getBoolean("success");
            if(success){
                Gson gson = new Gson();
                //get the login information property
                String loginInfo=responseObj.getString("loginInfo");
                //create java object from the JSON object
                Login login = gson.fromJson(loginInfo,Login.class);
                if(login.getPassword().equals(password)){
                    Intent goToRetailerItemList = new Intent();
                    goToRetailerItemList.setClass(this, RetailerItemListPage.class);
                    goToRetailerItemList.putExtra("username",username);
                    startActivity(goToRetailerItemList);
                }
                else{
                    Toast toast = Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                    //   errorMessage.setText();
                }



            }else{
                Toast toast = Toast.makeText(this, "Username doesn't exist! Please register!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


    }


}
