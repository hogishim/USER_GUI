package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<recipe_info> sample;

    public RecipeAdapter(Context context, ArrayList<recipe_info> data) {
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
    public recipe_info getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.poster);
        TextView recipeName = (TextView)view.findViewById(R.id.movieName);
        TextView grade = (TextView)view.findViewById(R.id.grade);

        imageView.setImageResource(R.drawable.img);

        Glide.with(mContext)
//                        .load(getRealPathFromURI(selectedImageUri))
                .load(sample.get(position).getUrl())
                .into(imageView);

        recipeName.setText(sample.get(position).getName());
        grade.setText(sample.get(position).getTag());

        return view;
    }
}