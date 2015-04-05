package product_exp.discountshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class RetailerAddItem extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_add_item);
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
        Intent it = new Intent();
        it.setClass(this, RetailerItemListPage.class);
        it.putExtra("Add Item", true);
        startActivity(it);
    }

    /*When clicking the camera button, user can use camera to capture picture*/
    public void onGet(View v) {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 100);
    }

    /*Pick the pictures from cellphone*/
    //public void onPick(View v) {
    //    Intent it = new Intent(Intent.ACTION_GET_CONTENT);
    //    it.setType("image/*");
    //    startActivityForResult(it, 101);
    //}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {

            //switch(requestCode) {
            //case 100:
            //    Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imgUri);
            //    sendBroadcast(it);
            //    break;

            //case 101:
            //    imgUri = convertUri(data.getData());
            //    break;
            //}

            /*Transform Intent object into Bundle object*/
            Bundle bd1 = data.getExtras();
            Bitmap bmp = (Bitmap) bd1.get("data");

            ImageView imv = (ImageView) findViewById(R.id.imageView);
            imv.setImageBitmap(bmp);
        } else {
            Toast.makeText(this, "You Take Picture Unsuccessfully!", Toast.LENGTH_LONG).show();
        }
    }
}
