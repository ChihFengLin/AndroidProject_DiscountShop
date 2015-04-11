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

import org.json.JSONException;
import org.json.JSONObject;

import webservice.JSONRequest;
import webservice.NetworkStatus;


public class RetailerRegister extends Activity {
    private BroadcastReceiver receiver;
    private EditText emailText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText retailerNameText;
    private EditText addressText;
    private EditText zipCodeText;

    private String email;
    private String username;
    private String password;
    private String retailerName;
    private String address;
    private String zipCode;
    private final String process_response_filter="action.createUser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_register);

        emailText=(EditText)findViewById(R.id.rremailEditText);
        usernameText=(EditText)findViewById(R.id.rrusernameEditText);
        passwordText=(EditText)findViewById(R.id.rrpasswordEditText);
        retailerNameText=(EditText)findViewById(R.id.rrshopnameEditText);
        addressText=(EditText)findViewById(R.id.rraddressEditText);
        zipCodeText=(EditText)findViewById(R.id.rrzipcodeEditText);
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
                if(responseType.trim().equalsIgnoreCase("createUser")){
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
        getMenuInflater().inflate(R.menu.menu_retailer_register, menu);
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
        askToCreateRetailer();

    }

    //sending...
    //ask to send JSON request
    private void askToCreateRetailer(){
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            email=emailText.getText().toString().trim();
            username=usernameText.getText().toString().trim();
            password=passwordText.getText().toString().trim();
            retailerName=retailerNameText.getText().toString().trim();
            address=addressText.getText().toString().trim();
            zipCode=zipCodeText.getText().toString().trim();
            //if not username was entered
            if (username.isEmpty()||password.isEmpty()||email.isEmpty()||retailerName.isEmpty()||address.isEmpty()||zipCode.isEmpty()){
                Toast toast = Toast.makeText(this, "Please don't leave the input blank!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }else{
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent= new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG,"createUser");
                msgIntent.putExtra("email",email);
                msgIntent.putExtra("username",username);
                msgIntent.putExtra("password",password);
                msgIntent.putExtra("retailerName",retailerName);
                msgIntent.putExtra("address",address);
                msgIntent.putExtra("zipCode",zipCode);
                msgIntent.putExtra("userType","retailer");
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
                Toast toast = Toast.makeText(this, "Creating account is successful!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                Intent goToRetailerItemList = new Intent();
                goToRetailerItemList.setClass(this, RetailerItemListPage.class);
                goToRetailerItemList.putExtra("username",username);
                startActivity(goToRetailerItemList);
            }else{
                Toast toast = Toast.makeText(this, "Creating account failure, maybe username does exist, Please try again!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


    }


}
