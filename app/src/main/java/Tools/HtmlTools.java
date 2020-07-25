package Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML工具类，主要用于提取HTML文档中的信息
 */
public class HtmlTools {
    public static String findlt(String html){
        String res = "";
        String pattern = "<input type=\"hidden\" name=\"lt\" value=\"(.*?)\" />";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(html);

        if (m.find()){
            res = m.group();
            res = res.substring(res.indexOf("value=\"")+7, res.lastIndexOf("\""));
        }
        return res;
    }
    public static String find_eventId(String html){
        String res = "";
        String pattern = "<input type=\"hidden\" name=\"_eventId\" value=\"(.*?)\" />";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(html);

        if (m.find()){
            res = m.group();
            res = res.substring(res.indexOf("value=\"")+7, res.lastIndexOf("\""));
        }
        return res;
    }

    public static String findexecution(String html){
        String res = "";
        String pattern = "<input type=\"hidden\" name=\"execution\" value=\"(.*?)\" />";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(html);

        if (m.find()){
            res = m.group();
            res = res.substring(res.indexOf("value=\"")+7, res.lastIndexOf("\""));
        }
        return res;
    }
}
