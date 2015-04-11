package product_exp.discountshop;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import controller.JSONRequest;
import com.google.gson.Gson;
import controller.NetworkStatus;
import model.Login;


public class ConsumerLogin extends Activity {

    private MyRequestReceiver receiver;
    private EditText usernameText;
    private EditText passwordText;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText=(EditText)findViewById(R.id.usernameEditText);
        passwordText=(EditText)findViewById(R.id.passwordEditText);

        // Register receiver so that this Activity can be notified
        // when the JSON response came back
        IntentFilter filter = new IntentFilter(MyRequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver=new MyRequestReceiver();
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
        Log.v("DiscountShopActivity", "onDestory");
        unregisterReceiver(receiver);
        super.onDestroy();
    }
    public void goItemList(View v) {

        getLoginInfo();


    }

    public void goRegister(View v) {
        Intent goToRegister = new Intent();
        goToRegister.setClass(this, ConsumerRegister.class);
        startActivity(goToRegister);
    }

    private void getLoginInfo(){
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
                msgIntent.putExtra("loginType","consumer");
                startService(msgIntent);
            }
        }
    }
    //broadcast receiver to receive messages sent from the JSON IntentService
    public class MyRequestReceiver extends BroadcastReceiver{
        public static final String PROCESS_RESPONSE="intent.action.PROCESS_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {
            String response= null;
            String responseType=intent.getStringExtra(JSONRequest.IN_MSG);
            if(responseType.trim().equalsIgnoreCase("getLoginInfo")){
                response=intent.getStringExtra(JSONRequest.OUT_MSG);
                processJsonResponse(response);
            }
            else if (responseType.trim().equalsIgnoreCase("getSomethingElse")){

            }
        }
    }
    //parse and display JSON response
    private void processJsonResponse(String response){
        JSONObject responseObj=null;
        TextView errorMessage = (TextView)findViewById(R.id.errorMessage);

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
                    Intent goToItemList = new Intent();
                    goToItemList.setClass(this, ItemListPage.class);
                    startActivity(goToItemList);
                }
                else{
                    errorMessage.setText("Invalid password");
                }



            }else{
                errorMessage.setText("Username doesn't exist! Please register!");
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


    }



}
