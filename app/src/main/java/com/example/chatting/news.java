package com.example.chatting;

public class news {
    private String headline;
    private String writer;
    private String context;

    public news(String headline,String writer,String context){
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
