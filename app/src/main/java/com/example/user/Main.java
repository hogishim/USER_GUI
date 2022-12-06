package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main extends Fragment {

    MainActivity mainActivity;

    List<recipe_info> contentRecipeList;
    List<recipe_info> userRecipeList;

    recipe_info selected_info;

    ArrayList<recipeIngredient> ingredient_list;
    ArrayList<recipeCooking> cooking_list;

    // 메인 액티비티 위에 올린다.
   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    // 메인 액티비티에서 내려 온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        Button button = rootView.findViewById(R.id.toList);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
                //mainActivity.fragmentChange(7);
            }

        });

        Button button2 = rootView.findViewById(R.id.toUd);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3);
            }

        });

        Button button3 = rootView.findViewById(R.id.toErl);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5);
            }

        });

        Button button4 = rootView.findViewById(R.id.toMyPg);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4);
            }

        });

        Button button5 = rootView.findViewById(R.id.vsrc);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(6);
            }

        });


        Button button6 = rootView.findViewById(R.id.src);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });

        return rootView;
    }

    public void get_recommend(int ID,int num){

        String Url = "http://172.30.1.52:8080/recommend";
        mainActivity.sendHttpApi("{\"number\" = "+ Integer.toString(num) +"\"}",Url,101,ID);

    }

    public void get_recipe_data(int con, int ID,int[] input){
        String Url = "http://172.30.1.52:8080/android";
        String JSON = null;
        try{
            switch (con){
                case 1010:
                    Url = Url + "/recipeList";

                    JSONObject tmp = new JSONObject();
                    tmp.put("find",input);
                    JSON = tmp.toString();
                    break;
                case 1011:
                    Url = Url + "/recipeList";
                    tmp.put("find",input);
                    JSON = tmp.toString();
                    break;
                case 1012:
                    Url = Url + "/recipeIngredient";
                    JSON = "";
                    break;
                case 1013:
                    Url = Url + "/recipeCooking";
                    JSON = "";
                    break;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        mainActivity.sendHttpApi(JSON,Url,con,ID);
    }

    public void set_recipe_info_list(JSONObject input, int con){

        try {
            JSONArray list = input.getJSONArray("recipelist");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_ID = tmp.getInt("ID");
                String tmp_image_url = tmp.getString("imgsrc");
                String tmp_Name = tmp.getString("Name");

                recipe_info tmp_info = new recipe_info(tmp_ID,tmp_image_url,tmp_Name);
                if(con == 0){
                    contentRecipeList.add(tmp_info);
                }else if(con == 1){
                    userRecipeList.add(tmp_info);
                }
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }

    }

    public ArrayList<recipeIngredient> set_recipeIngredient_list(JSONObject input){

        ArrayList<recipeIngredient> ingredient_list = new ArrayList<recipeIngredient>();

        try {
            JSONArray list = input.getJSONArray("recipeIngredient");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_idx = tmp.getInt("idx_ing");
                String tmp_Name = tmp.getString("ingredient_name");
                String tmp_CP = tmp.getString("ingredient_Cp");

                recipeIngredient tmp_ingredient = new recipeIngredient(tmp_idx,tmp_Name,tmp_CP,-1);

                ingredient_list.add(tmp_ingredient);
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }

        return ingredient_list;
    }

    public ArrayList<recipeCooking> set_recipeCooking_list(JSONObject input){

        ArrayList<recipeCooking> cooking_list = new ArrayList<recipeCooking>();

        try {
            JSONArray list = input.getJSONArray("recipe");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_idx = tmp.getInt("idx");
                String tmp_order = tmp.getString("cooking_order");
                int tmp_no = tmp.getInt("cooking_order_no");

                recipeCooking tmp_cook = new recipeCooking(tmp_idx,tmp_order,tmp_no,-1);

                cooking_list.add(tmp_cook);
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }

        return cooking_list;
    }

    public void send_result (String result, int con){

        JSONObject jsonObject = null;

        try {
            Log.d("result_json",result);
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.d("json_error","String to json Object fail1");
            e.printStackTrace();
        }

        if(con == 0){
            set_recipe_info_list(jsonObject,con);
        }else if (con == 1){
            set_recipe_info_list(jsonObject,con);
        }else if (con == 2){
            this.ingredient_list = set_recipeIngredient_list(jsonObject);
        }else if (con == 3){
            this.cooking_list = set_recipeCooking_list(jsonObject);
        }

        if(ingredient_list != null && cooking_list !=null){
            recipe_data ptr = new recipe_data(cooking_list,ingredient_list,selected_info);

            mainActivity.frameLayout7.setData(ptr);
            mainActivity.fragmentChange(7);
        }

    }

    public void send_recommand_result(String result){

        JSONObject recommand;

        int[] content_base_list = null;

        int[] user_base_list = null;

       try{
           recommand = new JSONObject(result);

           int num = recommand.getInt("num");

           JSONArray content = recommand.getJSONArray("content_base");
           JSONArray user = recommand.getJSONArray("user_base");

           content_base_list = new int[num];
           user_base_list = new int[num];

           for(int i=0;i<num;i++){
               content_base_list[i] = content.getJSONObject(i).getInt("ID");
               user_base_list[i] = user.getJSONObject(i).getInt("ID");
           }


       }catch (JSONException e){
           Log.d("json_error","err in recommand json");
           e.printStackTrace();
       }

       get_recipe_data(1010,-1,content_base_list);
       get_recipe_data(1011,-1,user_base_list);

    }


}