package Tools;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import Bean.CommunityItem;
import Bean.MessageItem;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class GraphTools<T> {
    private MessageItem newMessage;
    private CommunityItem newCommunity;
    private ArrayList<String> ImageUrls;

    public GraphTools(T item) {
        if (item instanceof MessageItem) {
            this.ImageUrls = ((MessageItem) item).getImages();
        }
        else
            this.ImageUrls = ((CommunityItem) item).getImages();
    }

    public void InitLuban(Context context) {
        Luban.with(context)
                .load(ImageUrls)
                .ignoreBy(100)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }
}
