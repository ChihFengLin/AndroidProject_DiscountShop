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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import intents.ClickInterface;
import intents.IntentFactory;
import model.Item;
import utility.MyAdapter;
import webservice.JSONRequest;
import webservice.NetworkStatus;


public class ConsumerItemListPage extends ListActivity implements AdapterView.OnItemClickListener{

    private BroadcastReceiver receiver;
    private static MyAdapter myAdapter;
    private String username, searchItemName;
    private EditText search;
    private final String process_response_filter="action.searchItemList";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);
        myAdapter = new MyAdapter(this);
        setListAdapter(myAdapter);

        /*Check whether a new item is added or not*/
        Intent it = getIntent();
        username = it.getStringExtra("username");
        if(it.getBooleanExtra("Add Item", false)) {
            myAdapter.addItem(myAdapter.getCount()+1);
            this.setSelection(myAdapter.getCount()+1);
        }

        /*Special part: android.R.id.list*/
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(this);

        /*Search bar*/
        search = (EditText) findViewById(R.id.edt1);
        // searchButton = (Button) findViewById(R.id.btn1);

        /*Register receiver so that this Activity can be notified
         when the JSON response came back
        set the receiver filter */
        IntentFilter filter = new IntentFilter(process_response_filter);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        /*implement the receiving details*/
        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String response= null;
                String responseType=intent.getStringExtra(JSONRequest.IN_MSG);
                if(responseType.trim().equalsIgnoreCase("getSearchItemList")){
                    response=intent.getStringExtra(JSONRequest.OUT_MSG);
                    // switch to another activity is included
                    processJsonResponse(response);
                }
            }
        };

        registerReceiver(receiver, filter);
    }

    /*Click different picture and jump to different item page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        ClickInterface click = IntentFactory.goToNext(this, DisplayItemDetail.class, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, Menu.FIRST, 0, "Profile Setting");
        menu.add(0, Menu.FIRST+1, 0, "Return Last Page");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case Menu.FIRST:
                ClickInterface click = IntentFactory.goToNext(this, ConsumerSetting.class, null, (Object)username);
                //Intent goToRetailerSetting = new Intent();
                //goToRetailerSetting.putExtra("username",username);
                //goToRetailerSetting.setClass(this, RetailerSettings.class);
                //startActivity(goToRetailerSetting);
                break;
            case Menu.FIRST+1:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goSearch(View v) {
        askToGetSearchItemList();
    }

    /*sending...
         ask to send JSON request*/
    private void askToGetSearchItemList(){
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            searchItemName = search.getText().toString();

            if (searchItemName.trim().isEmpty()){
                Toast toast = Toast.makeText(this, "Please input the item name!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
            }else{
                //pass the request to web service so that it can
                //run outside the scope of the main UI thread
                Intent msgIntent= new Intent(this, JSONRequest.class);
                msgIntent.putExtra(JSONRequest.IN_MSG,"getSearchItemList");
                msgIntent.putExtra("searchItemName",searchItemName.trim());
                msgIntent.putExtra("processType",process_response_filter);
                startService(msgIntent);
            }
        }
    }


    /*receiving...
      parse and display JSON response */
    private void processJsonResponse(String response){
        JSONObject responseObj=null;
        try {
            //create JSON object from JSON string
            responseObj = new JSONObject(response);
            //get the success property
            boolean success=responseObj.getBoolean("success");
            if(success){
                Gson gson = new Gson();
                //get the information property from servlet
                String searchItemList=responseObj.getString("searchItemList");
                //create java object from the JSON object
                ArrayList<Item> returnItemList = gson.fromJson(searchItemList, ArrayList.class);

                int count = returnItemList.size();

                Toast toast = Toast.makeText(this, count, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();


            }else{
                Toast toast = Toast.makeText(this, "There is no this item!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


    }
}
