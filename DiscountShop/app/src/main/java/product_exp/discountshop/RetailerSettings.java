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


public class RetailerSettings extends Activity {
    private BroadcastReceiver receiver;
    private String username;
    private EditText newAddressText;
    private String newAddress;
    private final String process_response_filter="action.update";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_settings);
        Intent it= getIntent();
        username=it.getStringExtra("username");
        newAddressText=(EditText)findViewById(R.id.editAddress);
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
                if(responseType.trim().equalsIgnoreCase("update")){
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
        getMenuInflater().inflate(R.menu.menu_retailer_settings, menu);
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
    public void updateAddress(View v){
    askToUpdateAddress();
    }
    public void goAddItem(View v) {
        Intent goToAddItem = new Intent();
        goToAddItem.setClass(this, RetailerAddItem.class);
        startActivity(goToAddItem);
    }

    public void goMain(View v) {
        Intent goToMain = new Intent();
        goToMain.setClass(this, MainActivity.class);
        startActivity(goToMain);
    }

    //sending...
    //ask to send JSON request
    private void askToUpdateAddress(){
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            newAddress=newAddressText.getText().toString().trim();
            //if not username was entered
            if (newAddress.isEmpty()){
                Toast toast = Toast.makeText(this, "Please don't leave the input blank!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }else{
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent= new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG,"update");
                msgIntent.putExtra("username",username);
                msgIntent.putExtra("newAddress",newAddress);
                msgIntent.putExtra("updateType","address");
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
                Toast toast = Toast.makeText(this, "Updating address is successful!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }else{
                Toast toast = Toast.makeText(this, "Updating address failure, Please try again!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


    }



}
