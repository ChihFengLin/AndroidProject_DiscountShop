package product_exp.discountshop;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



public class RetailerItemListPage extends ListActivity implements OnItemClickListener{

    private static MyAdapter myAdapter;
    private String username;

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

    /*Click different picture and jump to different item page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        Intent it = new Intent();
        it.setClass(this, RetailerUpdateItem.class);
        startActivity(it);
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
                Intent goToRetailerSetting = new Intent();
                goToRetailerSetting.putExtra("username",username);
                goToRetailerSetting.setClass(this, RetailerSettings.class);
                startActivity(goToRetailerSetting);
                break;
            case Menu.FIRST+1:
                finish();
                break;
            /*
            case Menu.FIRST:
                myAdapter.addItem(myAdapter.getCount()+1);
                this.setSelection(myAdapter.getCount()+1);
                break;
            case Menu.FIRST+1:
                myAdapter.removeItem(myAdapter.getCount()-1);
                break;
            case Menu.FIRST+2:
                finish();
                break;
            */
        }
        return super.onOptionsItemSelected(item);
    }



    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list_page, menu);
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

    */
}
