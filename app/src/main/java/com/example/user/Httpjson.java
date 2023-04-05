package com.example.user;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Httpjson extends AsyncTask<String, Void, JSONObject> {

    public String URL_String;
    public JSONObject json = null;

    public Httpjson(String U)
    {
        URL_String = U;
    }
    @Override
    protected JSONObject doInBackground(String... strings) {


        Log.d("onclicked", "onClick!: ");
        String param,param1, receiveMsg;
        String str;

        try {
            URL url = new URL(URL_String);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            param1 =
            param = "id=" + 0;
            StringBuilder stringBuilder = new StringBuilder();
            if (conn != null) {
                System.out.println("서버에 연결되었습니다");
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);
                conn.getOutputStream().write(
                        param.getBytes("utf-8"));
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) break;
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                }
                conn.disconnect();
            } else {
                Log.d("else", "연결안됨");
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//            Log.e("haha", jsonObject.toString());
            Log.d("TAG", String.valueOf(jsonObject instanceof JSONObject));
            json = jsonObject;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

}
