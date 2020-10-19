package com.example.chatting;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Bean.MessageItem;
import Bean.User;
import Tools.FTPUtils;
import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;

    public static User CurrentUser;
    public static SharedPreferences UserInfo;
    public static ThreadPoolExecutor Threads;
    public static FTPUtils mFTP;

//    public MyApplication() {
//
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Threads = new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        Bmob.initialize(this, "0c073307ca22bf3e1268f1f70bef2941");
        CurrentUser = new User();
        UserInfo = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        mFTP = new FTPUtils();
        mFTP.initFtpClient("47.103.215.161", 21, "ftpuser", "ftpuser");
    }

    //单例模式中获取唯一的MyApplication实例
    public static MyApplication getInstance() {
        if(null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity)  {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for(Activity activity:activityList) {
            activity.finish();
        }
        activityList.clear();
    }
}
