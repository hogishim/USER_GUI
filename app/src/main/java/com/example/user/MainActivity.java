package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Main frameLayout1;
    FoodList frameLayout2;
    TestAgain frameLayout3;
    UserPage frameLayout4;
    EnrollRecipe frameLayout5;
    VoiceAlert  frameLayout6;
    RecipeView frameLayout7;

    MyHandler handle = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d("permission","checkSelfPermission");
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Log.d("permission","shouldShowRequestPermissionRationale");
                // 사용자에게 설명을 보여줍니다.
                // 권한 요청을 다시 시도합니다.

            } else {
                // 권한요청

                Log.d("permission","권한 요청");
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_STATE},
                        1000);

            }
        }


        frameLayout1 = new Main();
        frameLayout2 = new FoodList();
        frameLayout3 = new TestAgain();
        frameLayout4 = new UserPage();
        frameLayout5 = new EnrollRecipe();
        frameLayout6 = new VoiceAlert();
        frameLayout7 = new RecipeView();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lgn, frameLayout1).commit();

    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1000: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // 권한 획득 성공
                    Log.d("permission","권한 획득 성공");

                } else {

                    // 권한 획득 실패
                    Log.d("permission","권한 획득 실패");
                }
                return;
            }

        }
    }

    public void fragmentChange(int index){
        if(index == 1){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout1).commit();
        }
        else if(index == 2){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout2).commit();
        }
        else if(index == 3){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout3).commit();
        }
        else if(index == 4){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout4).commit();
        }
        else if(index == 5){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout5).commit();
        }
        else if(index == 6){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout6).commit();
        }
        else if(index == 7){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout7).commit();
        }
    }

    public void sendHttpApi(String data, String uri, int control,int ID){

        http_protocol http = new http_protocol(data,uri,this.handle,control,ID);

        http.start();
    }

    public static class MyHandler extends Handler {
        private final WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity Activity) {
            weakReference = new WeakReference<MainActivity>(Activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            MainActivity activity = weakReference.get();

            String result;


            if (activity != null) {
                if(msg.what == 101) {
                    // http 클래스에서 JSON 데이터를 넘겨받은 경우.

                    result = (String) msg.obj;
                    activity.frameLayout1.send_recommand_result(result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.
                }else if(msg.what == 1010) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 0);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1011) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 1);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1012) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 2);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1013) {

                    result = (String) msg.obj;
                    activity.frameLayout1.send_result(result, 3);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 102) {

                    result = (String) msg.obj;
                    activity.frameLayout2.send_result(result, 0);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1021) {

                    result = (String) msg.obj;
                    Log.d("con1",result);
                    activity.frameLayout2.send_result(result, 1);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 1022) {

                    result = (String) msg.obj;
                    Log.d("con2",result);
                    activity.frameLayout2.send_result(result, 2);

                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.
                }else if(msg.what == 103) {

                    result = (String) msg.obj;
                    //activity.frameLayout3.send_result(result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 104){

                        result = (String) msg.obj;
                        //activity.frameLayout4.send_result(result);

                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 105){

                        result = (String) msg.obj;
                        activity.frameLayout5.send_result(result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 106){

                        result = (String) msg.obj;
                        Log.d("JSON", "handleMessage: " + result);

                        try{
                            JSONObject json = new JSONObject(result);
                            String intent = json.getString("intent");
                            int control = Integer.parseInt(intent);
                            Log.d("intent","result : "+ control);
                            activity.frameLayout6.Chat_result(control);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        //activity.frameLayout6.send_result(result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 107){

                        result = (String) msg.obj;
                        activity.frameLayout7.send_result(result);
                    // http 클래스에서 JSON 데이터를 넘겨받지 못한 경우.

                }else if(msg.what == 404){

                        result = "Error!";
                        activity.frameLayout6.send_result(result);

                }

            }
        }


    }



}