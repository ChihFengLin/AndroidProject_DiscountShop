package product_exp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

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

        txv1 = (TextView)findViewById(R.id.item);
        txv2 = (TextView)findViewById(R.id.price);
        txv3 = (TextView)findViewById(R.id.Retailer);
        imv1 = (ImageView)findViewById(R.id.imageView);

        Intent it= getIntent();
        txv1.setText(it.getStringExtra("item name"));
        txv2.setText(it.getStringExtra("item price"));
        txv3.setText(it.getStringExtra("retailer name"));

        imv1.setImageBitmap(ImageToBitmap(it.getStringExtra("picture")));

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
              .getMap();
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

