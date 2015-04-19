package product_exp.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import model.Base64;
import model.Item;
import product_exp.view.R;
import utility.MyAdapter;
import webservice.JSONRequest;
import webservice.NetworkStatus;
import android.view.View;
import android.widget.ImageView;

public class TestActivity extends Activity {

    private BroadcastReceiver receiver;
    private static MyAdapter myAdapter;
    private String username;
    private Bitmap image;
    private final String process_response_filter="action.getItem";
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView=(ImageView)findViewById(R.id.imageView2);

        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String response= null;
                String responseType=intent.getStringExtra(JSONRequest.IN_MSG);
                if(responseType.trim().equalsIgnoreCase("getItem")){
                    response=intent.getStringExtra(JSONRequest.OUT_MSG);
                    // switch to another activity is included
                    processJsonResponse(response);
                }
            }
        };

        IntentFilter filter = new IntentFilter(process_response_filter);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver,filter);

    }

    public void test(View v){
        askToGetItem();
        imageView.setImageBitmap(image);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    //sending...
    //ask to send JSON request
    private void askToGetItem(){
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            //pass the request to web service so that it can
            //run outside the scope of the main UI thread
            Intent msgIntent= new Intent(this, JSONRequest.class);
            msgIntent.putExtra(JSONRequest.IN_MSG,"getItem");
            // right now only use imageName to test
            msgIntent.putExtra("imageName","test");
            msgIntent.putExtra("processType",process_response_filter);
            startService(msgIntent);

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
                String itemInfo=responseObj.getString("itemInfo");
                //create java object from the JSON object
                Item item = gson.fromJson(itemInfo,Item.class);
                String ba1= item.getImage();
                Log.v("ba111111", ba1);

                try {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;
//
//                    // Calculate inSampleSize
//                    options.inSampleSize = calculateInSampleSize(options, 500, 500);
//                    // Decode bitmap with inSampleSize set
//                    options.inJustDecodeBounds = false;
//                    Bitmap bmp1=BitmapFactory.decodeByteArray(base64converted,0,base64converted.length,options);
                    byte[] decodedString = Base64.decode(ba1, Base64.NO_OPTIONS);
                    image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    if(image==null){
                        Log.v("error:","imageERROR");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                Toast toast = Toast.makeText(this, "get Image error! Please register!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }

    }

}
