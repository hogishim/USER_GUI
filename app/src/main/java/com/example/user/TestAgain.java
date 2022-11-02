package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TestAgain extends Fragment {

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


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test_again, container, false);

        Button button = rootView.findViewById(R.id.toL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
            }

        });

        Button button2 = rootView.findViewById(R.id.toM);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4);
            }

        });

        Button button3 = rootView.findViewById(R.id.toH);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        Button button4 = rootView.findViewById(R.id.toR);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5);
            }

        });

        return rootView;




    }
}