package Login;

import Bean.Internet;
import Bean.User;
import DAO.OkHttpDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.*;

public class JwloginActivity implements OkHttpDAO {
    private User mUser;
    private String mBaseURL;
    private String mlt;
    private String m_eventId;
    private String mexecution;


    /**
     * Http客户端
     */
    private OkHttpClient mOkHttpClient = new OkHttpClient
            .Builder()
            .cookieJar(new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
                    String u = httpUrl.host();
                    String urlStr = httpUrl.url().toString();
                    if (urlStr.equals(mBaseURL)){
                        cookieStore.put(u,cookies);
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
//                System.out.println(response.body().string());
//                mlt = HtmlTools.findlt(response.body().string());
//                m_eventId = HtmlTools.find_eventId(response.body().string());
//                mexecution = HtmlTools.findexecution(response.body().string());
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
    public void login(User user){
        setJwURL("https://sso.hitsz.edu.cn/cas/login");
        Internet NetDemo = new Internet();
        NetDemo.getData(mBaseURL);

        //表单信息
        FormBody body = new FormBody.Builder()
                .add("username",user.getName())
                .add("password",user.getPassword())
//                .add("lt",mlt)
//                .add("_eventId",m_eventId)
//                .add("execution",mexecution)
                .add("rememberMe","on")
                .add("lt",NetDemo.getLt())
                .add("execution",NetDemo.getExecution())
                .add("_eventId",NetDemo.get_eventid())
                .add("openid","")
                .add("vc_username","")
                .add("vc_password","")
                .build();

        Request request = new Request.Builder()
                .header("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("User-Agent", "(Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                .url(mBaseURL)
                .post(body)
                .build();
//        Response response = mOkHttpClient.newCall(request).execute();
//        if(response.isSuccessful()){
//            System.out.println(response.body().string());
//        }
//        else {
//            throw new IOException(String.valueOf(response));
//        }
//        mUser = user;
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
                System.out.println(html);
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

