package product_exp.discountshop;


import android.app.ListActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CustomItemListView extends ListActivity{


    public class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;

        public MyAdapter(Context c) {
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = myInflater.inflate(R.layout.myxml, null);

            ImageView logo = (ImageView) convertView.findViewById(R.id.imglogo);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView list = (TextView) convertView
                    .findViewById(R.id.txtengname);

            logo.setImageResource(logos[position]);
            name.setText(names[position]);
            list.setText(lists[position]);

            return convertView;
        }

    }

}
