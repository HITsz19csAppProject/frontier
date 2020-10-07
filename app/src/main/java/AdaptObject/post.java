package AdaptObject;

public class post {
    private String headline=null;
    private String writer=null;
    private String context=null;

    public post(String headline,String writer,String context){
        this.headline=headline;
        this.writer=writer;
        this.context=context;
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


}