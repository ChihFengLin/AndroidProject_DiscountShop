package product_exp.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import model.ProcessJSONInterface;
import intents.ClickInterface;
import intents.IntentFactory;
import utility.Base64;
import webservice.JSONRequest;
import webservice.NetworkStatus;

public class RetailerAddItem extends Activity implements ProcessJSONInterface{

    private String retailerTag;
    private BroadcastReceiver receiver;
    private Bitmap bmp;
    private String ba1;
    private EditText itemNameText;
    private EditText itemPriceText;
    private ImageView imv;

    private String itemName;
    private String itemPrice;
    private final String process_response_filter="action.addItem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_add_item);
        itemNameText=(EditText) findViewById(R.id.editTextItemName);
        itemPriceText= (EditText) findViewById(R.id.editTextItemPrice);
        imv = (ImageView) findViewById(R.id.imageView);
        bmp = ((BitmapDrawable)imv.getDrawable()).getBitmap();
        Intent it= getIntent();
        retailerTag=it.getStringExtra("username");

        IntentFilter filter = new IntentFilter(process_response_filter);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        // implement the receiving details,
        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String response= null;
                String responseType=intent.getStringExtra(JSONRequest.IN_MSG);
                if(responseType.trim().equalsIgnoreCase("addItem")){
                    response=intent.getStringExtra(JSONRequest.OUT_MSG);
                    // switch to another activity is included
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_retailer_add_item, menu);
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

    /* The action for click "Add Item" button*/
    public void addItem(View v) {

        askToAddItem();
    }

    /*When clicking the camera button, user can use camera to capture picture*/
    public void onGet(View v) {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            //selectedImage = data.getData();
            /*Transform Intent object into Bundle object*/
            Bundle bd1 = data.getExtras();
            bmp = (Bitmap) bd1.get("data");
            imv.setImageBitmap(bmp);

        } else {
            Toast.makeText(this, "You Take Picture Unsuccessfully!", Toast.LENGTH_LONG).show();
        }
    }


    //sending...
    //ask to send JSON request
    private void askToAddItem(){
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeBytes(ba);
        Log.e("base64", "------" + ba1);

        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            itemName=itemNameText.getText().toString();
            itemPrice=itemPriceText.getText().toString();
            //if not username was entered
            if (itemName.trim().isEmpty()||itemPrice.trim().isEmpty()){
                Toast toast = Toast.makeText(this, "Please enter item Name and item Price!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }
            else if (Float.parseFloat(itemPrice)>9999){
                Toast toast = Toast.makeText(this, "Please enter item price smaller than 10000!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }
            else{
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent= new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG,"addItem");
                msgIntent.putExtra("retailerTag",retailerTag);
                msgIntent.putExtra("itemName",itemName.trim());
                msgIntent.putExtra("itemPrice",itemPrice.trim());
                msgIntent.putExtra("image",ba1);
                msgIntent.putExtra("processType",process_response_filter);
                startService(msgIntent);
            }
        }
    }

    //receiving...
    //parse and display JSON response
    @Override
    public void processJsonResponse(String response){
        JSONObject responseObj=null;
        try {
            //create JSON object from JSON string
            responseObj = new JSONObject(response);
            //get the success property
            boolean success=responseObj.getBoolean("success");
            if(success){
                    Toast toast = Toast.makeText(this, "upload finished", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                    //   errorMessage.setText();
                ClickInterface click = IntentFactory.goToNext(this, RetailerItemListPage.class, null, (Object)retailerTag);

            }else{
                Toast toast = Toast.makeText(this, "upload failed!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }

    }

}
