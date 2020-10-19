package AdaptObject;

public class ReceivedNews {

    private String headline;
    private String writer;
    private String context;
    private String isRead;

    public ReceivedNews(String headline, String writer, String context, String isRead){
        this.headline=headline;
        this.writer=writer;
        this.context=context;
        this.isRead = isRead;
    }

    public void setIsRead(String str){
        this.isRead = str;
    }

    public ReceivedNews() {

    }

    public String getHeadline(){
        return headline;
    }

    public String getContext() {
        return context;
    }

    public String getWriter() {
        return writer;
    }

    public String getIsRead(){return isRead;}
}
