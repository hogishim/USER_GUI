package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class viewRecipeAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<recipeCooking> sample;

    public viewRecipeAdapter(Context context, ArrayList<recipeCooking> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public recipeCooking getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.recipe_item, null);

        TextView num = (TextView)view.findViewById(R.id.num);
        TextView Str = (TextView)view.findViewById(R.id.str);

        num.setText(Integer.toString(sample.get(position).getCooking_order_no()));
        Str.setText(sample.get(position).getCooking_order());

        return view;
    }
}
