package product_exp.discountshop;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RetailerItemListPage extends ListActivity {

    private MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);
        myAdapter = new MyAdapter(this);
        setListAdapter(myAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, Menu.FIRST, 0, "add item");
        menu.add(0, Menu.FIRST+1, 0, "remove item");
        return super.onCreateOptionsMenu(menu);

    }
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()){
            case Menu.FIRST:
                myAdapter.addItem(myAdapter.getCount()+1);
                this.setSelection(myAdapter.getCount()+1);
                break;
            case Menu.FIRST+1:
                myAdapter.removeItem(myAdapter.getCount()-1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
