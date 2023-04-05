package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class viewIngredientAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<recipeIngredient> sample;

    public viewIngredientAdapter(Context context, ArrayList<recipeIngredient> data) {
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
    public recipeIngredient getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.recipe_item, null);

        TextView num = (TextView)view.findViewById(R.id.num);
        TextView Str = (TextView)view.findViewById(R.id.str);

        num.setText(Integer.toString(position+1));
        Str.setText(sample.get(position).getIngredient_Name());

        return view;
    }
}
