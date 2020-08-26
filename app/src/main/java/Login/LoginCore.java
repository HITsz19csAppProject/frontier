package Login;

import android.content.SharedPreferences;

import androidx.annotation.WorkerThread;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.chatting.MyApplication.CurrentUser;

public class LoginCore {
    private HashMap<String, String> cookies;
    private Map<String, String> defaultRequestHeader;
    private String Url = "https://sso.hitsz.edu.cn/cas/login";

    public LoginCore(){
        cookies = new HashMap<>();
        defaultRequestHeader = new HashMap<String, String>();
        defaultRequestHeader.put("Connection", "keep-alive");
        defaultRequestHeader.put("Accept", "*/*");
        defaultRequestHeader.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
    }

    @WorkerThread
    public boolean Login(SharedPreferences UserInfo) throws IOException {
        Connection hc = Jsoup.connect(Url)
                .headers(defaultRequestHeader);
        cookies.clear();
        cookies.putAll(hc.execute().cookies());
        System.out.println(cookies.toString());
        String lt = null;
        String execution = null;
        String eventId = null;
        Document d = hc.cookies(cookies).get();
        lt = d.select("input[name=lt]").first().attr("value");
        execution = d.select("input[name=execution]").first().attr("value");
        eventId = d.select("input[name=_eventId]").first().attr("value");
        Connection c2 = Jsoup.connect(Url)
                .cookies(cookies)
                .headers(defaultRequestHeader)
                .ignoreContentType(true);
        Document page = c2.cookies(cookies)
                .data("username", CurrentUser.getUsername())
                .data("password", UserInfo.getString(CurrentUser.getUsername(), ""))
                .data("lt", lt)
                .data("rememberMe", "on")
                .data("execution", execution)
                .data("_eventId", eventId).post();
        cookies.putAll(c2.response().cookies());
        boolean login = page.toString().contains("注销账号");
        return login;
    }
}
