package product_exp.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import exception.DiscountShopException;
import model.ProcessJSONInterface;
import intents.ClickInterface;
import intents.IntentFactory;
import webservice.JSONRequest;
import com.google.gson.Gson;
import webservice.NetworkStatus;
import model.Login;
import android.util.Log;

import static exception.DiscountShopException.myExceptions.GPS_NOT_ENABLED;

public class ConsumerLogin extends Activity implements ProcessJSONInterface {

    private BroadcastReceiver receiver;
    private EditText usernameText;
    private EditText passwordText;
    private String username;
    private String password;
    private final String process_response_filter="action.getConsumerLoginInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText=(EditText)findViewById(R.id.usernameEditText);
        passwordText=(EditText)findViewById(R.id.passwordEditText);

        //set the consumer radius to 25 each time user logs into app.
        ConsumerSetting.itemRadius = 25;
        LocationManager lm = null;
        boolean gps_enabled = false, network_enabled = false;
        if(lm == null)
            lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        try {
            WifiManager mng = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            if ( !mng.isWifiEnabled()) throw new DiscountShopException(DiscountShopException.myExceptions.WIFI_NOT_ENABLED, "Wifi not enabled", this);
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!gps_enabled) throw new DiscountShopException(GPS_NOT_ENABLED, "GPS NOT enabled", this);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!network_enabled) throw new DiscountShopException(DiscountShopException.myExceptions.NOT_CONNECTED, "GPS NOT enabled", this);

        } catch(DiscountShopException e) {
               e.fix(e.getExceptionCaught());
                e.printStackTrace();
            }

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
        getMenuInflater().inflate(R.menu.menu_consumer_login, menu);
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
    public void goItemList(View v) {

        askToGetLoginInfo();

    }

    public void goRegister(View v) {

        ClickInterface click = IntentFactory.goToNext(this, ConsumerRegister.class, null, null);
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
            if (username.isEmpty()) {
                usernameText.setError("Username cannot be empty");
            }
            if (password.isEmpty()) {
                passwordText.setError("Password cannot be empty");
            }
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
                msgIntent.putExtra("loginType","consumer");
                msgIntent.putExtra("processType",process_response_filter);
                startService(msgIntent);
            }
        }
    }

    //receiving...
    //parse and display JSON response
    @Override
    public void processJsonResponse(String response){
        Log.v("returned response: ",response);
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
                    ClickInterface click = IntentFactory.goToNext(this, ConsumerItemListPage.class, null, null);
//                    Intent goToItemList = new Intent();
//                    goToItemList.setClass(this, ConsumerItemListPage.class);
//                    startActivity(goToItemList);
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
