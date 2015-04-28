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

import model.Base64;
import product_exp.view.R;

public class MyAdapter extends BaseAdapter{

    private LayoutInflater adapterLayoutInflater;
    private  ArrayList<Integer> arrayList;
    private Bitmap image;
    private String itemName;
    private float itemPrice;
    /*Constructor*/
    public MyAdapter(Context c){
        adapterLayoutInflater = LayoutInflater.from(c);
        arrayList = new ArrayList<Integer>();
    }

/*
////////////please modify this snippet, I just test retrieving image from database///////////

 */
    public void setImage(String image){

        try {
            byte[] decodedString = Base64.decode(image, Base64.NO_OPTIONS);
            this.image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            if(image==null){
                Log.v("error:", "imageERROR");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
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
        //tag.image.setImageBitmap(image);
        tag.image.setBackgroundResource(R.drawable.cheesecake);

        tag.itemName.setText(itemName);

        tag.itemPrice.setText("Price " + Float.toString(itemPrice));

        tag.itemDistance.setText("Distance " + arrayList.get(position));
        return view;
    }


}
