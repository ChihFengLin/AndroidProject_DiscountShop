package product_exp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import intents.ClickInterface;
import intents.IntentFactory;
import android.widget.*;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import model.Base64;
import model.Item;
import model.ItemList;
import webservice.JSONRequest;
import webservice.NetworkStatus;

import java.io.IOException;

public class RetailerUpdateDeleteItem extends FragmentActivity {

    private EditText itemNameEditText;
    private EditText itemPriceEditText;
    private ImageView itemImage;

    private String retailerName;
    private String itemName;

    private BroadcastReceiver receiver;
    private final String process_response_filter="action.updateItem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_update_item);
        itemNameEditText = (EditText)findViewById(R.id.item);
        itemPriceEditText = (EditText) findViewById(R.id.editprice);
        itemImage = (ImageView) findViewById(R.id.imageView);

        Intent it=getIntent();
        itemName=it.getStringExtra("item name");
        itemNameEditText.setText(itemName);
        itemPriceEditText.setText(it.getStringExtra("item price"));
        itemImage.setImageBitmap(ImageToBitmap(it.getStringExtra("picture")));
        retailerName = it.getStringExtra("retailer name");

        //set the receiver filter
        IntentFilter filter = new IntentFilter(process_response_filter);
        filter.addCategory(Intent.CATEGORY_DEFAULT);

        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String response=null;
                String responseType=intent.getStringExtra(JSONRequest.IN_MSG);
                if(responseType.trim().equalsIgnoreCase("updateItem")){
                    response=intent.getStringExtra(JSONRequest.OUT_MSG);
                    //switch to another activity is included
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver, filter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_retailer_update_item, menu);
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

    // after clicking update Item
    public void updateItem(View v) {
        String newItemName=itemNameEditText.getText().toString();
        String newItemPrice=itemPriceEditText.getText().toString();
        float price=Float.parseFloat(newItemPrice);
        if (price>0&&price<10000){
            askToUpdateItem(newItemName,newItemPrice);
        }else{
            Toast toast = Toast.makeText(this, "Please input the price between 0~9999.99", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
        }

    }

    //after clicking delete Item
    public void deleteItem(View v) {
        askToDeleteItem();
    }

    public Bitmap ImageToBitmap(String imageString){
        Bitmap image = null;
        try {
            byte[] decodedString = Base64.decode(imageString, Base64.NO_OPTIONS);
            image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            if(image==null){
                Log.v("error:", "imageERROR");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //sending...
    //ask to send JSON request
    private void askToDeleteItem(){
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            //pass the request to web service so that it can
            //run outside the scope of the main UI thread
            Intent msgIntent= new Intent(this, JSONRequest.class);
            msgIntent.putExtra(JSONRequest.IN_MSG,"updateItem");
            // right now only use imageName to test
            msgIntent.putExtra("retailerName",retailerName);
            msgIntent.putExtra("itemName",itemName);
            msgIntent.putExtra("updateDelete","delete");
            msgIntent.putExtra("processType",process_response_filter);
            startService(msgIntent);

        }
    }

    private void askToUpdateItem(String newItemName, String newItemPrice){
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            //pass the request to web service so that it can
            //run outside the scope of the main UI thread
            Intent msgIntent= new Intent(this, JSONRequest.class);
            msgIntent.putExtra(JSONRequest.IN_MSG,"updateItem");
            // right now only use imageName to test
            msgIntent.putExtra("retailerName",retailerName);
            msgIntent.putExtra("itemName",itemName);
            msgIntent.putExtra("newItemName",newItemName);
            msgIntent.putExtra("newItemPrice",newItemPrice);
            msgIntent.putExtra("updateDelete","update");
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
            boolean isDelete=responseObj.getBoolean("isDelete");
            if(success&&isDelete){
                Toast toast = Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                ClickInterface click = IntentFactory.goToNext(this, RetailerItemListPage.class, null, (Object)retailerName);

            }else if(success&&(!isDelete)){
                Toast toast = Toast.makeText(this, "update Success", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                ClickInterface click = IntentFactory.goToNext(this, RetailerItemListPage.class, null, (Object)retailerName);
            }
            else{
                Toast toast = Toast.makeText(this, "update or delete failed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }
    }


}
