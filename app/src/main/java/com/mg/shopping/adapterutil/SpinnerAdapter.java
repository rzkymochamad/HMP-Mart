package com.mg.shopping.adapterutil;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mg.shopping.R;


public class SpinnerAdapter extends BaseAdapter {

    ArrayList<Object> objects = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public SpinnerAdapter(Context context, List<Object> objects) {
        this.context = context;
        this.objects.addAll(objects);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //do nothing
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);



        if (position == 0) {

            TextView txtRecordType = (TextView) view.findViewById(R.id.txt_label);
            txtRecordType.setTextColor(Color.GRAY);

        }


        return view;
    }

    protected class DataHolder {
        TextView txtShippingCompany;
        TextView txtShippingTagline;

        public DataHolder(View view) {
            txtShippingCompany = (TextView) view.findViewById(R.id.txt_shipping_company);
            txtShippingTagline = (TextView) view.findViewById(R.id.txt_shipping_tagline);
        }
    }

    protected class LabelHolder {
        TextView txtLabel;


        public LabelHolder(View view) {
            txtLabel = (TextView) view.findViewById(R.id.txt_label);

        }
    }


}
