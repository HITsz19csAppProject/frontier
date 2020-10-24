package Tools;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.chatting.LabelActivity;
import com.example.chatting.PostActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Bean.MessageItem;
import Bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddLabels extends Application {
    public List<String> grade = new ArrayList<>();        //年级
    public List<String> Class = new ArrayList<>();        //班级
    public List<String> academy = new ArrayList<>();      //学院
    public List<String> speciality = new ArrayList<>();   //专业
    public List<User> ObjectiveUser = new ArrayList<>();


    /*public void addLabels()
    {
        BmobQuery<User> userGrade = new BmobQuery<User>();
        userGrade.addWhereContainedIn("Grade",grade);

        BmobQuery<User> userClass = new BmobQuery<User>();
        userGrade.addWhereContainedIn("ClassID",Class);

        BmobQuery<User> useracademy = new BmobQuery<User>();
        useracademy.addWhereContainedIn("",academy);

        BmobQuery<User> userSpeciality = new BmobQuery<User>();
        userGrade.addWhereContainedIn("Major",speciality);

        List<BmobQuery<User>> andQuerys = new ArrayList<BmobQuery<User>>();
        andQuerys.add(userGrade);
        andQuerys.add(userClass);
        //andQuerys.add(useracademy);
        andQuerys.add(userSpeciality);


        BmobQuery<User> query = new BmobQuery<User>();
        query.and(andQuerys);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    *//**
                     在此处对已选中的人进行下一步操作
                     *//*
                    for(User u:list)
                    {
                        System.out.println(u.getPhoneNum());
                    }
                    Intent intent = new Intent(AddLabels.this, LabelActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list_2",(Serializable)list);
                    intent.putExtras(bundle);
                    startActivity(intent);

//                    Intent intent = new Intent(AddLabels.this, PostActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("list",(Serializable) ObjectiveUser);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());//发生错误，返回错误代码
                }
            }
        });
    }*/

    public void setObjectiveUser(List<User> ObjectiveUser)
    {
        this.ObjectiveUser = ObjectiveUser;
    }
}
