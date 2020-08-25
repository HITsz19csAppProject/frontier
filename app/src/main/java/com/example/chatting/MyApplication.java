package com.example.chatting;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

import Bean.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MyApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;

    public static User CurrentUser = new User();

//    public MyApplication() {
//
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "0c073307ca22bf3e1268f1f70bef2941");
//        CurrentUser = BmobUser.getCurrentUser(User.class);
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
