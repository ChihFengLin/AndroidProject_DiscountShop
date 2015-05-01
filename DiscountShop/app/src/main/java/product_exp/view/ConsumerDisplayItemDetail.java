package product_exp.view;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.location.LocationManager;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import model.Base64;


public class ConsumerDisplayItemDetail extends FragmentActivity {
    private GoogleMap map;
    private TextView txv1, txv2, txv3;
    private ImageView imv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item_detail);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        txv1 = (TextView)findViewById(R.id.item);
        txv2 = (TextView)findViewById(R.id.price);
        txv3 = (TextView)findViewById(R.id.Retailer);
        imv1 = (ImageView)findViewById(R.id.imageView);

        Intent it= getIntent();
        txv1.setText(it.getStringExtra("item name"));
        txv2.setText(it.getStringExtra("item price"));
        txv3.setText(it.getStringExtra("retailer name"));
        imv1.setImageBitmap(ImageToBitmap(it.getStringExtra("picture")));


        String retailerName = it.getStringExtra("retailer name");
        //lat long set for trial
        //double lat = 40.433988;
        //double longt = -79.9226423;
        double lat = it.getDoubleExtra("latitude", 0);
        double longt = it.getDoubleExtra("longitude", 0);
        LatLng latlng = new LatLng(lat, longt);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
              .getMap();
        Marker newmarker = map.addMarker(new MarkerOptions().position(latlng).title(retailerName));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

