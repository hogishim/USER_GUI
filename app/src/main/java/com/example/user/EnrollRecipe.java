package com.example.user;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class EnrollRecipe extends Fragment {

    Button button2;
    ImageView imageView;
    EditText recipe_name;
    EditText recipe_tag;
    Uri selectedImageUri;
    ArrayList<recipeCooking> add_recipe_cook = new ArrayList<recipeCooking>();
    ArrayList<recipeIngredient> add_recipe_ingredient = new ArrayList<recipeIngredient>();

    private static final int REQUEST_CODE = 0;

    MainActivity mainActivity;
    recipe_info recipeInfo;
    ArrayList<recipeIngredient> recipeIngredients;
    ArrayList<recipeCooking> recipeCookings;
    String name;
    String url;
    JSONArray jsonArray = new JSONArray();
    JsonParser jsonParser = new JsonParser();
    JSONObject jsoned= new JSONObject();
    String saveRecipe;
    // 메인 액티비티 위에 올린다.

    int cook_num = 1;
    int ingredient_num = 1;

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

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

        verifyStoragePermissions(getActivity());

        recipe_name = rootView.findViewById(R.id.name);
        recipe_tag = rootView.findViewById(R.id.type);

        ListView ingredient_list = rootView.findViewById(R.id.id1);
        ListView recipe_list = rootView.findViewById(R.id.id2);

        viewRecipeAdapter recipeAdapter = new viewRecipeAdapter(getActivity(),add_recipe_cook);

        recipe_list.setAdapter(recipeAdapter);
        viewIngredientAdapter ingredientAdapter = new viewIngredientAdapter(getActivity(),add_recipe_ingredient);

        ingredient_list.setAdapter(ingredientAdapter);

        Button button = rootView.findViewById(R.id.bb);//뒤로가기 버튼
//        name = nameText.get
//        Button saveButton = rootView.findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.fragmentChange(1);
            }

        });

        imageView = rootView.findViewById(R.id.poster);//이미지띄우는 뷰

        Button button2 = rootView.findViewById(R.id.selectim);

        button2.setOnClickListener(new View.OnClickListener() { //갤러리에 요청코드 보내기
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, REQUEST_CODE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
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

        Button addRecipe = rootView.findViewById(R.id.addCook);

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());

                ad.setMessage("레시피를 입력해 주세요.");
                EditText et = new EditText(getActivity());
                ad.setView(et);

                ad.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = et.getText().toString();
                        recipeCooking tmp = new recipeCooking(0,result,cook_num,0);

                        add_recipe_cook.add(tmp);

                        recipeAdapter.notifyDataSetChanged();

                        cook_num++;
                        dialogInterface.dismiss();
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                ad.show();
            }
        });

        Button addIngredient = rootView.findViewById(R.id.addIngredient);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());

                ad.setMessage("재료정보를 입력해 주세요.");
                EditText et = new EditText(getActivity());
                ad.setView(et);

                ad.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = et.getText().toString();
                        recipeIngredient tmp = new recipeIngredient(0,result,0);

                        add_recipe_ingredient.add(tmp);

                        ingredientAdapter.notifyDataSetChanged();

                        ingredient_num++;
                        dialogInterface.dismiss();
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                ad.show();
            }
        });

        Button saveButton = rootView.findViewById(R.id.submit);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadSingleImg(selectedImageUri.toString());
            }
        });

        return rootView;
    }

    public void upload_call(String Url_input){
        String name = recipe_name.getText().toString();
        String Url = Url_input;
        String tag = recipe_tag.getText().toString();

        JSONObject recipe_info = new JSONObject();
        try {
            recipe_info.put("Name",name);
            recipe_info.put("Url",Url);
            recipe_info.put("Tag",tag);
        } catch (JSONException e) {
            Log.d("recipe_info_json","recipe_info_json make error");
            e.printStackTrace();
        }

        JSONArray recipe_cooking = new JSONArray();

        for(recipeCooking tmp:add_recipe_cook){
            JSONObject cook_tmp = new JSONObject();
            try {
                cook_tmp.put("cooking_order",tmp.cooking_order);
                cook_tmp.put("cooking_order_no",tmp.cooking_order_no);
            } catch (JSONException e) {
                Log.d("recipe_cooking_json","recipe_cooking_json make error");
                e.printStackTrace();
            }

            recipe_cooking.put(cook_tmp);

        }

        JSONArray recipe_ingredient = new JSONArray();

        for(recipeIngredient tmp:add_recipe_ingredient){
            JSONObject ingredient_tmp = new JSONObject();
            try {
                ingredient_tmp.put("ingredient_Name",tmp.getIngredient_Name());
            } catch (JSONException e) {
                Log.d("recipe_cooking_json","recipe_cooking_json make error");
                e.printStackTrace();
            }

            recipe_ingredient.put(ingredient_tmp);

        }

        JSONObject send_json = new JSONObject();

        try {
            send_json.put("recipe_info",recipe_info);
            send_json.put("recipe_cooking",recipe_cooking);
            send_json.put("recipe_ingredient",recipe_ingredient);
        } catch (JSONException e) {
            Log.d("send_json_make","send_json_make error");
            e.printStackTrace();
        }

        String server_Url = "http://172.30.1.52:8080/android/saveRecipe";
        Log.d("jsoned data",send_json.toString());

        upload_recipe(send_json,server_Url);
        selectedImageUri = null;
    }


    public void upload_recipe(JSONObject send_json,String server_Url) {

        mainActivity.sendHttpApi(send_json.toString(),server_Url,105,-1);

    }

    public void send_result(int control){

        if(control==0)//저장이 안되었을때
        {
            this.control(false);
            Toast.makeText(mainActivity.getApplicationContext(), "저장이 안됨", Toast.LENGTH_SHORT).show();
        }
        if(control==1)//저장이 잘되었을때
        {
            this.control(true);
            Toast.makeText(mainActivity.getApplicationContext(), "recipe saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void control(boolean tmp){

        if(tmp){//저장이 되었을때
            Log.d("saved","저장이 잘 되었음");
            recipe_name.setText("");
            mainActivity.fragmentChange(1);//메인프레그먼트로 이동
        }
        else{//저장이 안되었을때
            recipe_name.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK && data.getData() != null) {
                selectedImageUri = data.getData();
                Log.d("img", "MainActivity - onActivityResult() called" + selectedImageUri);
//                Log.d(TAG, "MainActivity - onActivityResult() called" + getRealPathFromURI(selectedImageUri));

                Glide.with(getActivity())
//                        .load(getRealPathFromURI(selectedImageUri))
                        .load(selectedImageUri)
                        .into(imageView);

            }
            try {
                Uri uri = data.getData();
                //Glide.with(getActivity()).load(uri).into(imageView); //다이얼로그 이미지사진에 넣기

            } catch (Exception e) {

            }
        }
    }

    private String getRealPathFromUri(Uri uri)
    {
        String[] proj=  {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return  url;
    }

    private void uploadSingleImg(String uri){
        try {

            String url = getRealPathFromUri(Uri.parse(uri));

            StorageReference storageReference = storage.getReference();

            Uri file = Uri.fromFile(new File(url));
            final StorageReference riversRef = storageReference.child("images/" + file.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file);

            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()) {

                        throw task.getException();
                    }
                    return riversRef.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Log.d("upload", "clear!");
                        Uri downloadUrl = task.getResult();
                        String ptr = downloadUrl.toString();

                        upload_call(ptr);
                    } else {
                        Toast.makeText(getActivity(), "업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("UPFILE", "upload uri fail", e);
        }

    }
}


