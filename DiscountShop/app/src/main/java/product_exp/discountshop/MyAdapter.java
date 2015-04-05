package product_exp.discountshop;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{

    private LayoutInflater adapterLayoutInflater;
    private  ArrayList<Integer> arrayList;

    /*Constructor*/
    public MyAdapter(Context c){
        adapterLayoutInflater = LayoutInflater.from(c);
        arrayList = new ArrayList<Integer>();
    }


    public void addItem(int position){
        arrayList.add(position);
        this.notifyDataSetChanged();
    }

    public void removeItem(int position){
        if(!arrayList.isEmpty()){
            arrayList.remove(position);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        /*ListView number*/
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        final TagView tag;
        if(view == null){
            view = adapterLayoutInflater.inflate(R.layout.custom_item_list_view, null);
            tag = new TagView(
                    (ImageView)view.findViewById(R.id.AdapterImage),
                    (TextView)view.findViewById(R.id.AdapterItemName),
                    (TextView)view.findViewById(R.id.AdapterItemPrice),
                    (TextView)view.findViewById(R.id.AdapterItemDistance));
            view.setTag(tag);
        }
        else{
            tag = (TagView)view.getTag();
        }
        /*Set the content on the widget*/
        tag.image.setBackgroundResource(R.drawable.cheesecake);
        tag.itemName.setText("Cheese Cake");
        tag.itemPrice.setText("Price " + arrayList.get(position));
        tag.itemDistance.setText("Distance " + arrayList.get(position));
        return view;
    }


}
