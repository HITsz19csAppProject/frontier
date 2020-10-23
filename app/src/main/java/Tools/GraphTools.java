package Tools;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import Bean.CommunityItem;
import Bean.MessageItem;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class GraphTools<T> {
    private ArrayList<String> ImageUrls;
    private ArrayList<String> CompressedImageUrls;

    public GraphTools(T item) {
        if (item instanceof MessageItem) {
            this.ImageUrls = ((MessageItem) item).getImages();
        }
        else
            this.ImageUrls = ((CommunityItem) item).getImages();
        CompressedImageUrls = new ArrayList<>();
    }

    public void compressBatch(Context context, T item) {
        if (ImageUrls == null || ImageUrls.size() == 0) {
            if (item instanceof MessageItem) {
                ((MessageItem) item).setCompressedImages(new ArrayList<>());
                new ServerTools().BeforeSaveMessage(context, (MessageItem) item);
            } else {
                ((CommunityItem) item).setCompressedImages(new ArrayList<>());
                new ServerTools().BeforeSaveCommunityMessage(context, (CommunityItem) item);
            }
            return;
        }
        final LinkedList<Runnable> taskList = new LinkedList<>();
        final Handler handler = new Handler();
        class Task implements Runnable {
            String path;

            Task(String path) {
                this.path = path;
            }

            @Override
            public void run() {
                Luban.with(context)
                        .setTargetDir(context.getExternalCacheDir().getAbsolutePath())
                        .load(new java.io.File(path))
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(java.io.File file) {
                                CompressedImageUrls.add(file.getPath());
                                if (!taskList.isEmpty()) {
                                    Runnable runnable = taskList.pop();
                                    handler.post(runnable);
                                } else {
                                    if (item instanceof MessageItem) {
                                        ((MessageItem) item).setCompressedImages(CompressedImageUrls);
                                        new ServerTools().BeforeSaveMessage(context, (MessageItem) item);
                                    } else {
                                        ((CommunityItem) item).setCompressedImages(CompressedImageUrls);
                                        new ServerTools().BeforeSaveCommunityMessage(context, (CommunityItem) item);
                                    }
                                    return;
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }).launch();    //启动压缩
            }
        }
        //循环遍历原始路径 添加至linklist中
        for (String path : ImageUrls) {
            taskList.add(new Task(path));
        }
        handler.post(taskList.pop());
    }
}