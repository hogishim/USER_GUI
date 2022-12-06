package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EnrollRecipe extends Fragment {

    Button button2;
    ImageView imageView;

    private static final int REQUEST_CODE = 0;

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

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_enroll_recipe, container, false);

        Button button = rootView.findViewById(R.id.bb);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        imageView = rootView.findViewById(R.id.poster);

       Button button2 = rootView.findViewById(R.id.selectim);

        button2.setOnClickListener(new View.OnClickListener() { //갤러리에 요청코드 보내기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });



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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
                try {
                    Uri uri = data.getData();
                    //Glide.with(getActivity()).load(uri).into(imageView); //다이얼로그 이미지사진에 넣기

                } catch (Exception e) {


            }
        }
    }

    public void upload_recipe(String Name, String Url, String[] recipe_cook, String[] recipe_ingredient, String[] ingredient_cp){

        JSONObject send_json = new JSONObject();

        try {

            send_json.put("recipeName",Name);
            send_json.put("recipeUrl",Url);

            JSONArray cook = new JSONArray();

            for(int i=0;i<recipe_cook.length;i++){
                JSONObject tmp_cook = new JSONObject();
                tmp_cook.put("recipe_String",recipe_cook[i]);
                tmp_cook.put("recipe_no",i);

                cook.put(tmp_cook);
            }

            send_json.put("recipe",cook);

            JSONArray ingredient = new JSONArray();

            for(int i=0;i<recipe_ingredient.length;i++){
                JSONObject tmp_ingredient = new JSONObject();
                tmp_ingredient.put("ingredient_Name",recipe_ingredient[i]);
                tmp_ingredient.put("ingredient_Cp",ingredient_cp[i]);
                tmp_ingredient.put("ingredient_no",i);

                ingredient.put(ingredient);
            }

            send_json.put("ingredient",ingredient);

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }

        String server_Url = "http://172.30.1.52:8080/android/recipeadd";
        mainActivity.sendHttpApi(send_json.toString(),server_Url,105,-1);

    }

    public void send_result(String result){
        Log.d("result",result);
    }
