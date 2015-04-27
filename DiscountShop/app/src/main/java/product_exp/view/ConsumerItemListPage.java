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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


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
    private final String process_response_filter1="action.getWholeItemList";
    private Item[] returnItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);

        myAdapter = new MyAdapter(this);
        setListAdapter(myAdapter);

        /*Check whether a new item is added or not*/
        //Intent it = getIntent();
        //username = it.getStringExtra("username");
        //if(it.getBooleanExtra("Add Item", false)) {
        //    myAdapter.addItem(myAdapter.getCount()+1);
        //    this.setSelection(myAdapter.getCount()+1);
        //}

        /*Special part: android.R.id.list*/
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(this);

        /*Search EditText*/
        search = (EditText) findViewById(R.id.edt1);

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
                } //else if (responseType.trim().equalsIgnoreCase("getWholeItemList")) {
                  //  response=intent.getStringExtra(JSONRequest.OUT_MSG);
                  //  processJsonResponse1(response);
                //}
            }
        };

        registerReceiver(receiver, filter);
    }

    /*Click different picture and jump to different item page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        ClickInterface click = IntentFactory.goToNext(this, ConsumerDisplayItemDetail.class, null, null);
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
                break;
            case Menu.FIRST+1:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Here should get item list from database based on setting distance*/
    //@Override
    //public void onResume() {

        /* Get wholeItemList first from remote server*/
    //    askToGetWholeItemList();

    //    for (int i = 0; i < wholeItemList.length; i++) {
    //        myAdapter.setImage(wholeItemList[i].getImage());
    //        myAdapter.setItemName(wholeItemList[i].getItemName());
    //        myAdapter.setItemPrice(wholeItemList[i].getItemPrice());
    //        myAdapter.addItem(myAdapter.getCount()+1);
    //        this.setSelection(myAdapter.getCount()+1);
    //    }
    //}


    public void goSearch(View v) {
        askToGetSearchItemList();

        //askToGetWholeItemList();
        //System.out.println(returnItemList.length);

        //for (int i = 0; i < returnItemList.length; i++) {
        //    myAdapter.setImage(returnItemList[i].getImage());
        //    myAdapter.setItemName(returnItemList[i].getItemName());
        //    myAdapter.setItemPrice(returnItemList[i].getItemPrice());
        //    myAdapter.addItem(myAdapter.getCount()+1);
        //    this.setSelection(myAdapter.getCount()+1);
        //}
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


    /*sending...
         ask to send JSON request*/
    private void askToGetWholeItemList() {
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet){
            //pass the request to web service so that it can
            //run outside the scope of the main UI thread
            Intent msgIntent= new Intent(this, JSONRequest.class);
            msgIntent.putExtra(JSONRequest.IN_MSG,"getWholeItemList");
            msgIntent.putExtra("wholeItemListTag", "success");
            msgIntent.putExtra("processType",process_response_filter1);
            startService(msgIntent);
        }
    }



    /*receiving...
      parse and display JSON response */
    private void processJsonResponse(String response){
        JSONObject responseObj = null;
        try {
            //create JSON object from JSON string
            responseObj = new JSONObject(response);
            //get the success property
            boolean success=responseObj.getBoolean("success");
            if(success){
                Gson gson = new Gson();
                //get the information property from servlet
                String searchItemList = responseObj.getString("searchItemList");
                returnItemList = gson.fromJson(searchItemList, Item[].class);

                int count = returnItemList.length;

                Toast toast = Toast.makeText(this, Integer.toString(count), Toast.LENGTH_SHORT);
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


    /*receiving...
      parse and display JSON response */
    /*
    private void processJsonResponse1(String response){
        JSONObject responseObj = null;
        try {
            //create JSON object from JSON string
            responseObj = new JSONObject(response);
            //get the success property
            boolean success=responseObj.getBoolean("success");
            if(success){
                Gson gson = new Gson();
                //get the information property from servlet
                String returnWholeItemList = responseObj.getString("wholeItemList");
                wholeItemList = gson.fromJson(returnWholeItemList, Item[].class);

                int count = wholeItemList.length;

                Toast toast = Toast.makeText(this, Integer.toString(count), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();


            }else{
                Toast toast = Toast.makeText(this, "Get Consumer Item List Fail!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 105, 50);
                toast.show();
                //  errorMessage.setText();
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


    }
    */
}
