package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RecipeView extends Fragment {


    MainActivity mainActivity;
    String result;
    recipe_data[] recipeList;
    int index;

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
        // 이 하단 블럭은 테스트위해 임시로 제작한 레시피 데이터셋 더미.
        String[] tmp_recipe = {"1","2","3","4","5"};
        String[] tmp_item = {"1","2","3"};
        int tmp_maxRecipe = 5;
        int tmp_maxItem = 3;

        recipe_data tmp = new recipe_data(tmp_item,tmp_recipe,tmp_maxRecipe, tmp_maxItem);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_view, container, false);

        Button button = rootView.findViewById(R.id.tobc);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
            }

        });

        Button button2 = rootView.findViewById(R.id.strt);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.frameLayout6.setData(tmp);
                mainActivity.fragmentChange(6);
            }

        });


        return rootView;

    }

    public void send_result (String result){
        this.result = result;
    }
}
