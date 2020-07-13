package com.example.Login;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity {
    private String url = "https://sso.hitsz.edu.cn/cas/login";
    private String usr;
    private String pwd;

    private void get(){
        //获取okHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //构建Request对象
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        //构建call对象
        Call call = client.newCall(request);
        //异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Failed",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("Success",result);
            }
        });
    }

    private void post(){
        //创建OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //创建RequestBody(Form表达)
//        RequestBody body = new FormBody.Builder()
//                .add("username",usr)
//                .add("password",pwd)
//                .build();
        //创建RequestBody
        Map m = new HashMap();
        m.put("username",usr);
        m.put("password",pwd);
        JSONObject jsonObject = new JSONObject(m);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
                        ,jsonStr);
        //创建Request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("User-Agent", "(Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                .post(requestBodyJson)
                .build();
        //创建call回调对象
        final Call call = client.newCall(request);
        //发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
            }
        });
    }
}
