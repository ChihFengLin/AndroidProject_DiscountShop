package utility;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import model.Base64;
import model.Item;
import product_exp.view.R;

public class MyAdapter extends BaseAdapter{

    private LayoutInflater adapterLayoutInflater;
    private  ArrayList<Item> arrayList;
    private ArrayList<Double> distanceList;

    /*Constructor*/
    public MyAdapter(Context c){
        adapterLayoutInflater = LayoutInflater.from(c);
        arrayList = new ArrayList<Item>();
        distanceList = new ArrayList<Double>();
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

    public void addItem(int position, Item addedItem, double distance){
        arrayList.add(position, addedItem);
        distanceList.add(position, distance);
        this.notifyDataSetChanged();
    }

    public void removeAllItem(){
        arrayList.clear();
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
         TagView tag;
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
        //tag.image.setBackgroundResource(R.drawable.cheesecake);
        tag.image.setImageBitmap(ImageToBitmap(arrayList.get(position).getImage()));
        tag.itemName.setText(arrayList.get(position).getItemName());
        tag.itemPrice.setText("Price :" + Float.toString(arrayList.get(position).getItemPrice()));
        tag.itemDistance.setText("Distance " + Double.toString(distanceList.get(position)));
        return view;
    }


}
