package Login;

import Bean.User;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TempLoginActivity {
    private HashMap<String, String> cookies;
    private Map<String, String> defaultRequestHeader;
    private String Url = "https://sso.hitsz.edu.cn/cas/login";

    public TempLoginActivity(){
        cookies = new HashMap<>();
        defaultRequestHeader = new HashMap<String, String>();
        defaultRequestHeader.put("Connection", "keep-alive");
        defaultRequestHeader.put("Accept", "*/*");
        defaultRequestHeader.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
    }

    public boolean Login(String usr, String pwd) throws IOException {
        Connection hc = Jsoup.connect(Url)
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
        Connection c2 = Jsoup.connect(Url)
                .cookies(cookies)
                .headers(defaultRequestHeader)
                .ignoreContentType(true);
        Document page = c2.cookies(cookies)
                .data("username", usr)
                .data("password", pwd)
                .data("lt", lt)
                .data("rememberMe", "on")
                .data("execution", execution)
                .data("_eventId", eventId).post();
        cookies.putAll(c2.response().cookies());
        boolean login = page.toString().contains("注销账号");
        return login;
    }
}
