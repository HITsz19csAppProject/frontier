package AdaptObject;

import java.util.ArrayList;

public class news {
    private String headline;
    private String writer;
    private String context;
    private ArrayList<String> imageNames;


    public news(String headline, String writer, String context, ArrayList<String> imageNames){
        this.headline=headline;
        this.writer=writer;
        this.context=context;
        this.imageNames = imageNames;
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

    public ArrayList<String> getImageNames() { return imageNames; }
}
