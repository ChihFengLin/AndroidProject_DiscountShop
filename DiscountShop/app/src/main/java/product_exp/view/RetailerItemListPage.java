package product_exp.view;


import android.app.ListActivity;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

import intents.ClickInterface;
import intents.IntentFactory;
import utility.MyAdapter;
import webservice.JSONRequest;
import webservice.NetworkStatus;
import model.Item;
import model.ItemList;

public class RetailerItemListPage extends ListActivity implements OnItemClickListener{

    private BroadcastReceiver receiver;
    private MyAdapter myAdapter;
    private String username;
    //private Item[] returnItemList;
    private ItemList returnItemList;

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

        /*Special part: android.R.id.list*/
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(this);

        //set the receiver filter
        IntentFilter filter = new IntentFilter(process_response_filter);
        filter.addCategory(Intent.CATEGORY_DEFAULT);

        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String response=null;
                String responseType=intent.getStringExtra(JSONRequest.IN_MSG);
                if(responseType.trim().equalsIgnoreCase("getItem")){
                    response=intent.getStringExtra(JSONRequest.OUT_MSG);
                    //switch to another activity is included
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver, filter);

    }

    @Override
    protected void onResume(){
        super.onResume();

        myAdapter.removeAllItem();
        askToGetItem();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /*Click different picture and jump to different item page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        ClickInterface click = IntentFactory.goToNext(this, RetailerUpdateDeleteItem.class, returnItemList.getItem(position), null);

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
                msgIntent.putExtra("retailerName",username);
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
                //get the item list
                String itemList=responseObj.getString("itemList");
                //create java object from the JSON object
               returnItemList = gson.fromJson(itemList,ItemList.class);

                for(int i=0; i<returnItemList.getItemNum();i++){
                    Item newItem = returnItemList.getItem(i);
                    myAdapter.addItem(i,newItem, 0);
                    this.setSelection(i);
                }

            }else{
                Toast toast = Toast.makeText(this, "There is no item, please add!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
