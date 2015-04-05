package product_exp.discountshop;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ItemListPage extends ListActivity implements AdapterView.OnItemClickListener{

    private MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);
        myAdapter = new MyAdapter(this);
        setListAdapter(myAdapter);

        /*Check whether a new item is added or not*/

        myAdapter.addItem(myAdapter.getCount()+1);
        this.setSelection(myAdapter.getCount()+1);


        /*Special part: android.R.id.list*/
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setOnItemClickListener(this);
    }

    /*Click different picture and jump to different item page*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        Intent it = new Intent();
        it.setClass(this, DisplayItemDetail.class);
        startActivity(it);
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
                Intent goToConsumerSetting = new Intent();
                goToConsumerSetting.setClass(this, ConsumerSetting.class);
                startActivity(goToConsumerSetting);
                break;
            case Menu.FIRST+1:
                finish();
                break;
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
