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
import android.widget.Toast;


public class VoiceAlert extends Fragment {
    MainActivity mainActivity;

    Button buttonNext;
    Button buttonPrev;
    TextView tmpTextView;
    ProgressBar bar;
    String result = null;
    // 메인 액티비티 위에 올린다.

    recipe_data data = null;
    String recipeNowString;

    int current_index = 0;
    int maxIndex = 0;

    int barCurrentValue = 0;
    int barMaxValue = 0;
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

        bar = rootView.findViewById(R.id.progressBar);
        tmpTextView.setText(recipeNowString);

        barCurrentValue = bar.getProgress();
        barMaxValue = bar.getMax();


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

                control(true);
                ResetText();

            }
        });

        buttonPrev = rootView.findViewById(R.id.baku);

        buttonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                control(false);
                ResetText();

            }
        });

        return rootView;
    }

    public void control(boolean tmp){
        if(barMaxValue == barCurrentValue) {
            barCurrentValue = 0;
            current_index = 0;
        } else {
            bar.setVisibility(View.VISIBLE);
            if(tmp){
                barCurrentValue += (barMaxValue / maxIndex);
                current_index += 1;
                if(current_index > maxIndex){
                    Toast.makeText(mainActivity.getApplicationContext(),"마지막 페이지 입니다.",Toast.LENGTH_SHORT).show();
                    current_index = maxIndex;
                }
            }else{
                barCurrentValue -= (barMaxValue / maxIndex);
                current_index -= 1;
                if(current_index < 0){
                    Toast.makeText(mainActivity.getApplicationContext(),"첫 페이지 입니다.",Toast.LENGTH_SHORT).show();
                    current_index = 0;
                }
            }
        }
        bar.setProgress(barCurrentValue);
    }

    public void ResetText(){
        recipeNowString = data.getRecipeData(current_index);
        tmpTextView.setText(recipeNowString);
    }

    public void request_Chat(String JSON, String Url){
        mainActivity.sendHttpApi(JSON,Url,106);
    }

    public void send_result (String result){
        this.result = result;
    }

    public void setData (recipe_data input) {
        this.data = input;
        current_index = 0;
        maxIndex = this.data.getMaxPage();
        recipeNowString = this.data.getRecipeData(current_index);
    }
}