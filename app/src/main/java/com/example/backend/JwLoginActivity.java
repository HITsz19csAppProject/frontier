package com.example.backend;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwLoginActivity {
    private String username;
    private String password;
    private String cookie;
    private String result = null;
    private String url = "https://sso.hitsz.edu.cn/cas/login";
    private String host = "https://sso.hitsz.edu.cn";

    public void setPassword(String Password) {
        this.password = Password;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public boolean LoginMain(String Password, String Username) throws IOException {
        setPassword(Password);
        setUsername(Username);

        Map<String, String> RequestHead = new HashMap<>();
        RequestHead.put("Connection", "keep-alive");
        RequestHead.put("Accept", "*/*");
        RequestHead.put("User-Agent", "(Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");

        Map<String, String> getLoginInfo = new HashMap<>();
        Document doc = Jsoup.parse(new URL(url), 5000);
        Elements element = doc.select("#fm1");
//        System.out.println(element);

        getLoginInfo.put("lt", element.select("input[name=lt]").first().attr("value"));
        getLoginInfo.put("execution", element.select("input[name=execution]").first().attr("value"));
        getLoginInfo.put("_eventId", element.select("input[name=_eventId]").first().attr("value"));
        getLoginInfo.put("action", host + element.attr("action"));

//        for (String s : getLoginInfo.keySet()){
//            System.out.println(s+":"+getLoginInfo.get(s));
//        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(getLoginInfo.get("action"));

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lt", getLoginInfo.get("lt")));
        params.add(new BasicNameValuePair("execution", getLoginInfo.get("execution")));
        params.add(new BasicNameValuePair("_eventId", getLoginInfo.get("_eventId")));

        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() != 200) {
            System.out.println(response.getStatusLine().getStatusCode());
        }

        Header[] map = response.getAllHeaders();
//        System.out.println("显示Headers：");
//        for (Header entry : map){
//            System.out.println("Key : " + entry.getName() + " ,Value : " + entry.getValue());
//        }

        for (Header entry : map) {
            if (entry.getName().contains("Set-Cookie")) {
                cookie += entry.getValue() + ";";
            }
        }
//        System.out.println("显示Cookie信息："+cookie);

        HttpClient httpClient1 = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Cookie", cookie);

        HttpResponse response1 = httpClient1.execute(httpPost);
        int statusCode = response1.getStatusLine().getStatusCode();
        if (statusCode != 200) {
        }
        HttpEntity entity = response1.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
//        System.out.println("显示result："+result);

        if (result.contains("个人信息")) {
            return true;
        } else {
            return false;
        }
    }
}

