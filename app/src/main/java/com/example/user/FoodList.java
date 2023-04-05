package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FoodList extends Fragment {


    ArrayList<recipe_info> recipeDataList = null;
    ArrayList<recipeCooking> cooking_list = null;
    ArrayList<recipeIngredient> ingredient_list = null;
    recipe_info selected_info = null;

    MainActivity mainActivity;

    JSONObject jsonObject = null;

    RecipeAdapter recipeAdapter = null;
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

        ListView listView = rootView.findViewById(R.id.recommend_content);

        this.InitializeRecipeData();

        this.get_recipe_data(102,-1);

        recipeAdapter = new RecipeAdapter(getActivity(), recipeDataList);

        listView.setAdapter(recipeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){

                selected_info = recipeDataList.get(position);
                Log.d("sel_info",Integer.toString(selected_info.getID()));
                int ID = selected_info.getID();
                load_recipe(ID);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });



        ImageButton button6 = rootView.findViewById(R.id.toL);

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
        /*ImageButton button_ex = rootView.findViewById(R.id.toE);

        button_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = 0;
                mainActivity.frameLayout7.setData(tmp_load_recipe(ID)); // 지금은 그냥 0번 인덱스로 퉁쳐져 있으나 차후 해당 아이템의 recipe_data 인덱스를 찾아서 넘겨줘야 함.
                mainActivity.fragmentChange(7);
                //일단 지금은 방식 전검을 위해 이런식으로 아직 빈 버튼에 구현하였지만 추후 보여줄 아이템 별로 해당 아이템에 같은 구조로 로직 처리 해야함
            }

        });*/

        return rootView;

    }


    public void load_recipe(int ID){
        // 추가 개발 필요 (스프링 서버 http 요청, ID 값에 해당하는 레시피 추가 데이터 호출 및 데이터화.)
        // 필요 데이터를 어캐 끌어다 가져오는가에 대해선 위에 tmp 함수 형태처럼 만들면 됨.

        get_recipe_data(1021,ID);

        get_recipe_data(1022,ID);

    }


    public void InitializeRecipeData()
    {
        recipeDataList = new ArrayList<recipe_info>();
    }

    public void get_recipe_data(int con, int ID){
        String Url = "http://172.30.1.52:8080/android";
        switch (con){
            case 102:
                Url = Url + "/recipeList";
                break;
            case 1021:
                Url = Url + "/recipeIngredient";
                break;
            case 1022:
                Url = Url + "/recipeCooking";
                break;
        }

        String JSON = "";
        mainActivity.sendHttpApi(JSON,Url,con,ID);
    }

    public void set_recipe_info_list(JSONObject input){

        try {
            JSONArray list = input.getJSONArray("recipelist");

            for(int i=0;i< list.length();i++){
                JSONObject tmp = list.getJSONObject(i);

                int tmp_ID = tmp.getInt("ID");
                String tmp_image_url = tmp.getString("imgsrc");
                String tmp_Name = tmp.getString("Name");
                String tmp_tag = tmp.getString("recipe_tag");

                recipe_info tmp_info = new recipe_info(tmp_ID,tmp_image_url,tmp_Name,tmp_tag);

                recipeDataList.add(tmp_info);
            }

        } catch (JSONException e) {
            Log.d("json_error", "String to json Object fail");
            e.printStackTrace();
        }
        this.recipeAdapter.notifyDataSetChanged();
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
            set_recipe_info_list(jsonObject);
        }else if (con == 1){
            this.ingredient_list = set_recipeIngredient_list(jsonObject);
        }else if (con == 2){
            this.cooking_list = set_recipeCooking_list(jsonObject);
        }

        if(ingredient_list != null && cooking_list !=null){
            recipe_data ptr = new recipe_data(cooking_list,ingredient_list,selected_info);

            mainActivity.frameLayout7.setData(ptr);
            mainActivity.fragmentChange(7);
        }

    } // http_protocol 부분을 Httpjson 으로 변경 (Asynctask 관련 문제로 인한 미지원)

}