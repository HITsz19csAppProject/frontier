package Login;

import android.content.SharedPreferences;

import androidx.annotation.WorkerThread;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

import Tools.OkHttpUtil;

import static com.example.chatting.MyApplication.CurrentUser;

public class LoginCore {
    private HashMap<String, String> cookies;
    private Map<String, String> defaultRequestHeader;
    private String Url = "https://sso.hitsz.edu.cn/cas/login";
    private int code;

    public LoginCore(){
        cookies = new HashMap<>();
        defaultRequestHeader = new HashMap<String, String>();
        defaultRequestHeader.put("Connection", "keep-alive");
        defaultRequestHeader.put("Accept", "*/*");
        defaultRequestHeader.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
    }

    @WorkerThread
    public Boolean Login(SharedPreferences UserInfo) throws IOException {
        Connection hc = Jsoup.connect("http://jw.hitsz.edu.cn/cas")
                .headers(defaultRequestHeader);
        cookies.clear();
        cookies.putAll(hc.execute().cookies());
        String lt = null;
        String execution = null;
        String eventId = null;
        Document d = hc.cookies(cookies).get();
        lt = d.select("input[name=lt]").first().attr("value");
        execution = d.select("input[name=execution]").first().attr("value");
        eventId = d.select("input[name=_eventId]").first().attr("value");
        Connection c2 = Jsoup.connect("https://sso.hitsz.edu.cn:7002/cas/login?service=http%3A%2F%2Fjw.hitsz.edu.cn%2FcasLogin")
                .cookies(cookies)
                .timeout(10000)
                .headers(defaultRequestHeader)
                .ignoreContentType(true);
        Document page = c2.cookies(cookies)
                .data("username", CurrentUser.getUsername())
                .data("password", UserInfo.getString(CurrentUser.getUsername(), ""))
                .data("lt", lt)
                .data("rememberMe", "on")
                .data("execution", execution)
                .data("_eventId", eventId)
                .post();
        System.out.println("登录结束");
        cookies.putAll(c2.response().cookies());
        System.out.println(page.toString());

        return page.toString().contains("信息查询");
    }

    public void TempLogin(SharedPreferences UserInfo) throws IOException {
        Response response = OkHttpUtil.getSync(Url);
        System.out.println(response.body().toString());
    }
}
