package product_exp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import intents.ClickInterface;
import intents.IntentFactory;
import model.Base64;

public class RetailerAddItem extends Activity {

    private Bitmap bmp;
    private String picturePath;
    private Uri selectedImage;
    private String ba1;
    public static String URL = "http://wwww.codeee.com:8080/DiscountShopWebService/AddItemServlet";

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
        // upload item
        Log.e("path", "---------" + picturePath);

        // Image
        //Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeBytes(ba);


        Log.e("base64", "------" + ba1);

        // upload image to server
        new uploadToServer().execute();
        ImageView imvd = (ImageView) findViewById(R.id.imageView);
        imvd.setImageResource(R.mipmap.ic_launcher);
        Log.e("new", "------");
        // test for fun
        try {
            byte[] decodedString = Base64.decode(ba1);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            ImageView imv = (ImageView) findViewById(R.id.imageView);
            imv.setImageBitmap(decodedByte);
        } catch (IOException e) {
            e.printStackTrace();
        }


          ClickInterface click = IntentFactory.goToNext(this, RetailerItemListPage.class, null, null);

    }

    /*When clicking the camera button, user can use camera to capture picture*/
    public void onGet(View v) {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {

            selectedImage = data.getData();
            /*Transform Intent object into Bundle object*/
            Bundle bd1 = data.getExtras();
            bmp = (Bitmap) bd1.get("data");
            ImageView imv = (ImageView) findViewById(R.id.imageView);
            imv.setImageBitmap(bmp);

        } else {
            Toast.makeText(this, "You Take Picture Unsuccessfully!", Toast.LENGTH_LONG).show();
        }
    }




    private class uploadToServer extends AsyncTask<Void, Void, String> {
        private ProgressDialog pd = new ProgressDialog(RetailerAddItem.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait for image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            //nameValuePairs.add(new BasicNameValuePair("imageName", System.currentTimeMillis() + ".jpg"));
            nameValuePairs.add(new BasicNameValuePair("imageName", "test"));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection" + e.toString());
            }

            return "Success";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }


}
