package com.example.user;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class http_protocol extends Thread{
    String mURL = null;
    String body = null;
    int ID = -1;
    MainActivity.MyHandler handler = null;
    int control;

    public http_protocol(String input,String uri,MainActivity.MyHandler handler, int control,int ID){

        body = input;

        mURL = uri;

        this.ID = ID;

        this.handler = handler;

        this.control = control;
    }

    public String httpPostBodyConnection(String UrlData, String ParamData, int ID) {

        Log.d("http","body : "+ParamData);
        String result = "";
        String line = null;
        InputStream in = null;
        BufferedReader reader = null;
        try{

            if(this.control == 1021 || this.control == 1022 || this.control == 1012 || this.control == 1013 ){
                String param = "?id=" + Integer.toString(ID);
                Log.d("param",param);
                UrlData = UrlData+param;
            }

            URL url = new URL(UrlData);


            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoInput(true);
            con.setUseCaches(false);
            con.setReadTimeout(20000);
            con.setConnectTimeout(20000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true); //OutputStream 을 사용해서 post body 데이터 전송

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = ParamData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            con.connect();

            int responseCode = con.getResponseCode();
            Log.d("http","response_code : "+responseCode);
            Log.d("http","response : "+con.getResponseMessage());

            con.setInstanceFollowRedirects(true);

            if(responseCode == HttpsURLConnection.HTTP_OK){

                in = con.getInputStream();

            }else{
                in = con.getErrorStream();
            }

            reader = new BufferedReader(new InputStreamReader(in));
            while((line = reader.readLine())!=null){
                result += line;
            }

            reader.close();

            if(con !=null){
                con.disconnect();
            }

        }catch(Exception e){

            Log.d("error",e.toString());
            result = null;
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d("http","data : "+result);

        return result;
    }

    @Override
    public void run() {

        String result = httpPostBodyConnection(mURL,body,ID);

        if(result != null){
            Message message = handler.obtainMessage(control,result);
            handler.sendMessage(message);
        }else{
            Message message = handler.obtainMessage(404,"error");
            handler.sendMessage(message);
        }

    }

}