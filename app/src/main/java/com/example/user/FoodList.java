package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FoodList extends Fragment {

    MainActivity mainActivity;
    // 메인 액티비티 위에 올린다.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_food_list, container, false);

        Button button = rootView.findViewById(R.id.tob);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        Button button2 = rootView.findViewById(R.id.move);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(7);
            }

        });


        Button button3 = rootView.findViewById(R.id.toH);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        Button button4 = rootView.findViewById(R.id.toM);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4);
            }

        });

        Button button5 = rootView.findViewById(R.id.toT);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3);
            }

        });

        Button button6 = rootView.findViewById(R.id.toE);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5);
            }

        });

        return rootView;




    }
}