package product_exp.view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import intents.ClickInterface;
import intents.IntentFactory;
import model.Item;
import utility.MyAdapter;
import webservice.JSONRequest;
import webservice.NetworkStatus;


public class ConsumerItemListPage extends ListActivity implements AdapterView.OnItemClickListener, LocationListener {

    private BroadcastReceiver receiver;
    private static MyAdapter myAdapter;
    private String username, searchItemName;
    private EditText search;
    private final String process_response_filter="action.searchItemList";
    private final String process_response_filter1="action.getWholeItemList";
    private Item[] returnItemList;
    private LocationManager locationManager;
    private String provider;
    private Location myLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);
        myLocation = new Location("my location");

        myAdapter = new MyAdapter(this);
        setListAdapter(myAdapter);

        /*Check whether a new item is added or not*/
        //Intent it = getIntent();
        //username = it.getStringExtra("username");
        //if(it.getBooleanExtra("Add Item", false)) {
        //    myAdapter.addItem(myAdapter.getCount()+1);
        //    this.setSelection(myAdapter.getCount()+1);
        //}

        /*Hard Code*/
        myAdapter.setItemName("Cheese Cake");
        myAdapter.setItemPrice(15);
        myAdapter.addItem(myAdapter.getCount()+1);
        this.setSelection(myAdapter.getCount()+1);

        //find my location
        NetworkStatus networkStatus = new NetworkStatus();
        boolean internet = networkStatus.isNetworkAvailable(this);
        if(internet == false) {
            Toast toast = Toast.makeText(this, "Device not connected to Internet!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
            return;
        }


        LocationManager lm = null;
        boolean gps_enabled = false,network_enabled = false;
        if(lm==null)
            lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try{
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){}
        try{
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex){}

        if(!gps_enabled && !network_enabled){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();

        }

        //get new items location
        double lat = 40.433988;
        double longt = -79.9226423;
        LatLng latlng = new LatLng(lat, longt);
        Location retailerLoc = new Location("retailer");
        retailerLoc.setLatitude(lat);
        retailerLoc.setLongitude(longt);
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }

        //if the distance between them is less than what customer wants
        double distance = myLocation.distanceTo(retailerLoc);
        if (distance > 5) {
            Toast.makeText(this, "greater than 5 ",
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Distance = "+distance,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Distance = "+distance,
                    Toast.LENGTH_SHORT).show();
        }

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

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        unregisterReceiver(receiver);
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
    /*Click different picture and jump to different item page*/
    /*Hard Code! Later we can directly send Item object into next page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        ClickInterface click = IntentFactory.goToNext(this, ConsumerDisplayItemDetail.class, "Cheese Cake", "2558 Berryessa Rd San Jose, CA");
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

    private void checkGPSStatus() {

    }
}
