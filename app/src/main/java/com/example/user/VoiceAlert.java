package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class VoiceAlert extends Fragment {
    MainActivity mainActivity;


    Button buttonNext;
    Button buttonPrev;
    ProgressBar bar;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_voice_alert, container, false);

        Button button = rootView.findViewById(R.id.bcbc);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(7);
            }

        });

        buttonNext = rootView.findViewById(R.id.nex);

        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bar = rootView.findViewById(R.id.progressBar);

                int nowValue = bar.getProgress();
                int maxValue = bar.getMax();


                if(maxValue == nowValue) {
                    nowValue = 0;
                } else {

                    bar.setVisibility(View.VISIBLE);
                    nowValue += 5;

                }



                bar.setProgress(nowValue);




            }
        });

        buttonPrev = rootView.findViewById(R.id.baku);

        buttonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bar = rootView.findViewById(R.id.progressBar);

                int nowValue = bar.getProgress();
                int maxValue = bar.getMax();


                if(maxValue == nowValue) {
                    nowValue = 0;
                } else {

                    bar.setVisibility(View.VISIBLE);
                    nowValue -= 5;

                }



                bar.setProgress(nowValue);




            }
        });

        return rootView;
    }
}