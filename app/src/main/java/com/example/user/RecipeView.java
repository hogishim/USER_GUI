package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

public class RecipeView extends Fragment {


    MainActivity mainActivity;
    String result;
    recipe_data data;
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
        // 이 하단 블럭은 테스트위해 임시로 제작한 레시피 데이터셋 더미


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

                mainActivity.frameLayout6.setData(data);
                mainActivity.fragmentChange(6);

            }

        });

        ListView ingredient_list = rootView.findViewById(R.id.ingredient_list);
        ListView recipe_list = rootView.findViewById(R.id.recipe_list);

        viewRecipeAdapter recipeAdapter = new viewRecipeAdapter(getActivity(),data.getCooking_list());

        recipe_list.setAdapter(recipeAdapter);
        viewIngredientAdapter ingredientAdapter = new viewIngredientAdapter(getActivity(),data.getIngredient_list());

        ingredient_list.setAdapter(ingredientAdapter);

        ImageView imageView = (ImageView)rootView.findViewById(R.id.showim);

        Glide.with(getActivity())
//                        .load(getRealPathFromURI(selectedImageUri))
                .load(data.getInfo().getUrl())
                .into(imageView);

        ImageButton button6 = rootView.findViewById(R.id.toList);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
            }

        });

        ImageButton button7 = rootView.findViewById(R.id.toM);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4);
            }

        });

        ImageButton button8 = rootView.findViewById(R.id.toH);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        ImageButton button9 = rootView.findViewById(R.id.toT);

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3);
            }

        });


        ImageButton button10 = rootView.findViewById(R.id.toE);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5);

            }

        });

        return rootView;

    }

    public void setData(recipe_data tmp){
        this.data = tmp;
    }

    public void send_result (String result){
        this.result = result;
    }

}

