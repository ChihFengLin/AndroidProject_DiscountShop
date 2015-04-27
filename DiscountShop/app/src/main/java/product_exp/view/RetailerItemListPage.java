package product_exp.view;


import android.app.ListActivity;
import android.content.BroadcastReceiver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import intents.ClickInterface;
import intents.IntentFactory;
import model.Base64;
import utility.MyAdapter;
import webservice.JSONRequest;
import webservice.NetworkStatus;
import model.Item;

public class RetailerItemListPage extends ListActivity implements OnItemClickListener{

    private BroadcastReceiver receiver;
    private static MyAdapter myAdapter;
    private String username;
    private Bitmap image;
    private final String process_response_filter="action.getItem";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_item_list_page);

        myAdapter = new MyAdapter(this);
        setListAdapter(myAdapter);

        /*Check whether a new item is added or not*/
        Intent it = getIntent();
        username=it.getStringExtra("username");

        if(it.getBooleanExtra("Add Item", false)) {
            myAdapter.addItem(myAdapter.getCount()+1);
            this.setSelection(myAdapter.getCount()+1);
        }

        /*Special part: android.R.id.list*/
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(this);
    }

//    @Override
//    public void onResume(){
//
//    }


    /*Click different picture and jump to different item page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        ClickInterface click = IntentFactory.goToNext(this, RetailerUpdateItem.class, null, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, Menu.FIRST, 0, "Profile & Item Setting");
        menu.add(0, Menu.FIRST+1, 0, "Return Last Page");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case Menu.FIRST:
                ClickInterface click = IntentFactory.goToNext(this, RetailerSettings.class, null, (Object)username);

                break;
            case Menu.FIRST+1:
                finish();
                break;

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
                Log.v("ba111111",ba1);

                try {
                    byte[] decodedString = Base64.decode(ba1,Base64.NO_OPTIONS);
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
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


    }

}
