package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;


public class SignUp extends Fragment {


    MainActivity mainActivity;
    EditText idcheck;
    Button idcheckButton;
    EditText pw1;
    EditText pw2;
    Button signupButton;
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

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);

//        Button button = rootView.findViewById(R.id.v);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.fragmentChange(1);
//            }
//
//        });


        idcheck = rootView.findViewById(R.id.id1);
        idcheckButton = rootView.findViewById(R.id.check);
        pw1 = rootView.findViewById(R.id.pw1);
        pw2 = rootView.findViewById(R.id.pw2);
        signupButton = rootView.findViewById(R.id.su);


        idcheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idcheck.getText().toString();
                String JSON = "{\"email\":\""+id+"\"}";
                String URLS="http://10.0.2.2:8080/user/idcheck";
                try {
                    URL url = new URL(URLS);
                    mainActivity.sendHttpApi(JSON, String.valueOf(url),108,-1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idcheck.getText().toString();
                String URLS="";
                try {
                    URL url = new URL(URLS);
                    mainActivity.sendHttpApi(id, String.valueOf(url),101,-1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });


        return rootView;
    }
    public void control(boolean tmp){

            if(tmp){
                idcheck.setText("");
            }else{
                Log.d("haha","dd");
            }
        }

    public void check_result(int control){
        /*
        control : Chat_result 에 따른 제어 처리.
            0: timer
            1: next
            2: before
            3: repeat
        */
        if (control == 0) {
            this.control(true);
            Toast.makeText(mainActivity.getApplicationContext(), "아이디 중복", Toast.LENGTH_SHORT).show();
        } else if(control == 1){
            Toast.makeText(mainActivity.getApplicationContext(), "아이디 사용가능", Toast.LENGTH_SHORT).show();

        }
    }
}