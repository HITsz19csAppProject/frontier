package com.example.backend.Login;

import com.example.backend.Bean.Internet;
import com.example.backend.Bean.User;
import com.example.backend.DAO.OkHttpDAO;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity implements OkHttpDAO {
    private User mUser;
    private String mBaseURL;

    /**
     * Http客户端
     */
    private OkHttpClient mOkHttpClient = new OkHttpClient
            .Builder()
            .cookieJar(new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    String u = httpUrl.host();
                    String urlStr = httpUrl.url().toString();
                    if (urlStr.equals(mBaseURL)){
                        cookieStore.put(u,list);
                    }
                }

                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            }).build();

    public void init() {
        Request request = new Request
                .Builder()
                .url(mBaseURL)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {

            }

            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void sendPostRequest(String url, String ref) {

    }

    /**
     * 使用工具：OkHttpClient+Jsoup
     * 提交方式：提交表单，利用OkHttp构造表单进行post提交，Jsoup用于获取隐藏数据
     * 根据提交表单后返回的信息判断是否提交成功
     * @param user 用户信息
     */
    public void login(User user) {
        Internet NetDemo = new Internet();
        NetDemo.getData(mBaseURL);
        FormBody body = new FormBody
                .Builder()
                .add("username",user.getName())
                .add("password",user.getPassword())
                .add("rememberMe","on")
                .add("lt",NetDemo.getLt())
                .add("_eventId",NetDemo.get_eventid())
                .add("execution",NetDemo.getExecution())
                .add("openid","")
                .add("vc_username","")
                .add("vc_password","")
                .build();
        Request request = new Request.Builder()
                .url(mBaseURL)
                .post(body)
                .build();
        mUser = user;
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String html = body.string();
                String ErrorMessage = "";
                //检测是否有登录错误的信息，有则记录信息，否则登录成功
                String p = "<script language='javascript' defer>alert\\('([\\s\\S]+?)'\\);document\\.getElementById\\('([\\s\\S]+?)'\\)\\.focus\\(\\);</script>";
                Pattern pattern = Pattern.compile(p);
                Matcher m = pattern.matcher(html);

                if(!m.find()){
                    System.out.println("登录成功");
                }
            }
        });
    }

    public void setJwURL(String url) {
        mBaseURL = url;
        init();
    }
}
