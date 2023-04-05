package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main extends Fragment {

    MainActivity mainActivity;

    ArrayList<recipe_info> contentRecipeList;
    ArrayList<recipe_info> userRecipeList;

    recipe_info selected_info = new recipe_info(0,"","라면","라면,짠맛");

    ArrayList<recipeIngredient> ingredient_list;
    ArrayList<recipeCooking> cooking_list;

    RecipeAdapter contentRecipeAdapter;
    RecipeAdapter userRecipeAdapter ;

    int user_id = 4;

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

        TextView Last_info = rootView.findViewById(R.id.Last_info);

        TextView Last_tag = rootView.findViewById(R.id.Last_tag);

        Last_info.setText(selected_info.getName());

        Last_tag.setText(selected_info.getTag());

        ImageButton button2 = rootView.findViewById(R.id.toL);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
            }

        });

        ImageButton button3 = rootView.findViewById(R.id.toM);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4);
            }

        });

        ImageButton button4 = rootView.findViewById(R.id.toH);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        ImageButton button15 = rootView.findViewById(R.id.toT);

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3);
            }

        });


        ImageButton button16 = rootView.findViewById(R.id.toE);

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5);

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



        ImageButton button40 = rootView.findViewById(R.id.kor);

        button40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });




        ImageButton button41 = rootView.findViewById(R.id.jap);

        button41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });


        ImageButton button43 = rootView.findViewById(R.id.chi);

        button43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });




        ImageButton button44 = rootView.findViewById(R.id.wes);

        button44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });




        ImageButton button45 = rootView.findViewById(R.id.ckn);

        button45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });




        ImageButton button46 = rootView.findViewById(R.id.cow);

        button46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });


        ImageButton button47 = rootView.findViewById(R.id.pig);

        button47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });




        ImageButton button48 = rootView.findViewById(R.id.she);

        button48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });





        ImageButton button49 = rootView.findViewById(R.id.spi);

        button49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });




        ImageButton button50 = rootView.findViewById(R.id.sal);

        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });


        ImageButton button51 = rootView.findViewById(R.id.swe);

        button51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });




        ImageButton button52 = rootView.findViewById(R.id.sou);

        button52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);

            }

        });

        ListView contentlistView = rootView.findViewById(R.id.recommend_content);
        ListView userlistView = rootView.findViewById(R.id.recommend_user);

        this.InitializeRecipeData();

        this.get_recommend(selected_info.getID(),user_id,0,2);

        contentRecipeAdapter = new RecipeAdapter(getActivity(), contentRecipeList);
        userRecipeAdapter = new RecipeAdapter(getActivity(), userRecipeList);

        contentlistView.setAdapter(contentRecipeAdapter);
        userlistView.setAdapter(userRecipeAdapter);

        contentlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){

                selected_info = contentRecipeList.get(position);
                Log.d("sel_info",Integer.toString(selected_info.getID()));
                int ID = selected_info.getID();
                load_recipe(ID);

            }
        });

        userlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){

                selected_info = userRecipeList.get(position);
                Log.d("sel_info",Integer.toString(selected_info.getID()));
                int ID = selected_info.getID();
                load_recipe(ID);

            }
        });

        return rootView;
    }

    public void load_recipe(int ID){
        // 추가 개발 필요 (스프링 서버 http 요청, ID 값에 해당하는 레시피 추가 데이터 호출 및 데이터화.)
        // 필요 데이터를 어캐 끌어다 가져오는가에 대해선 위에 tmp 함수 형태처럼 만들면 됨.

        get_recipe_data(1012,ID,null);

        get_recipe_data(1013,ID,null);

    }

    public void InitializeRecipeData()
    {
        this.contentRecipeList = new ArrayList<recipe_info>();
        this.userRecipeList = new ArrayList<recipe_info>();
    }

    public void get_recommend(int recipe_id,int user_id,int type, int num){

        String Url = "http://9059-35-237-67-214.ngrok.io/recommend";
        mainActivity.sendHttpApi("{\"last_ID\" : "+ Integer.toString(recipe_id) +",\"user_ID\" : "+ Integer.toString(user_id) +",\"type\" : "+ Integer.toString(type) +",\"num\" : "+ Integer.toString(num) +"}",Url,101,recipe_id);

    }

    public void get_recipe_data(int con, int ID,int[] input){
        String Url = "http://172.30.1.52:8080/android";
        String JSON = null;
        try{
            switch (con){
                case 1010:
                    Url = Url + "/recipeListFind";

                    JSONObject tmp = new JSONObject();
                    JSONArray tmp_arr = new JSONArray();
                    for(int i : input){
                        JSONObject input_tmp = new JSONObject();
                        input_tmp.put("ID",i);
                        tmp_arr.put(input_tmp);
                    }
                    tmp.put("find",tmp_arr);
                    JSON = tmp.toString();
                    break;
                case 1011:
                    Url = Url + "/recipeListFind";

                    JSONObject ptr = new JSONObject();
                    JSONArray ptr_arr = new JSONArray();
                    for(int i : input){
                        JSONObject input_tmp = new JSONObject();
                        input_tmp.put("ID",i);
                        ptr_arr.put(input_tmp);
                    }
                    ptr.put("find",ptr_arr);
                    JSON = ptr.toString();
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
                String tmp_tag = tmp.getString("recipe_tag");

                recipe_info tmp_info = new recipe_info(tmp_ID,tmp_image_url,tmp_Name,tmp_tag);
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
        this.contentRecipeAdapter.notifyDataSetChanged();
        this.userRecipeAdapter.notifyDataSetChanged();

    }

    public ArrayList<recipeIngredient> set_recipeIngredient_list(JSONObject input){

        ArrayList<recipeIngredient> ingredient_list = new ArrayList<recipeIngredient>();

        try {
            JSONArray list = input.getJSONArray("recipeIngredient");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_idx = tmp.getInt("idx_ing");
                String tmp_Name = tmp.getString("ingredient_name");

                recipeIngredient tmp_ingredient = new recipeIngredient(tmp_idx,tmp_Name,-1);

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
               content_base_list[i] = content.getInt(i);
               user_base_list[i] = user.getInt(i);
           }


       }catch (JSONException e){
           Log.d("json_error","err in recommand json");
           e.printStackTrace();
       }

       get_recipe_data(1010,-1,content_base_list);
       get_recipe_data(1011,-1,user_base_list);

    }


}