package Bean;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Internet {
    /**
     * Form格式爬取网页基本类
     */
    private String url;
    private String lt;
    private String _eventid;
    private String execution;

    public String getLt() {
        return lt;
    }

    public String get_eventid() {
        return _eventid;
    }

    public String getExecution() {
        return execution;
    }

    public void setUrl(String URL){
        this.url = URL;
    }
    public void setLt(String lt){
        this.lt = lt;
    }
    public void set_eventid(String eventid){
        this._eventid = eventid;
    }
    public void setExecution(String Execution){
        this.execution = Execution;
    }

    public void getData(String URL){
        setUrl(URL);
        Document doc = null;
        try{
            Connection con = Jsoup.connect(url);
            con.header("User-Agent", "(Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                    .header("Accept", "*/*")
                    .header("Connection", "keep-alive");
            Connection.Response rs = con.execute();
            doc = Jsoup.parse(rs.body());
        }catch (Exception e){

        }
//        System.out.println(doc);
        Elements element = doc.select("#fm1");
        setLt(element.select("input[name=lt]").first().attr("value"));
        set_eventid(element.select("input[name=_eventId]").first().attr("value"));
        setExecution(element.select("input[name=execution]").first().attr("value"));
    }
}