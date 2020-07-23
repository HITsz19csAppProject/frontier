package com.example.Notice;

import android.widget.Toast;

import com.example.Bean.NoticeItem;
import com.example.Bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class NoticeAddActivity {
    private String NoticeContent;
    private String NoticeTitle;

    public void NoticePublish(){
        NoticeItem bean = new NoticeItem();
        User user = BmobUser.getCurrentUser(User.class);
        bean.setAuthor(user)
                .setContent(NoticeContent)
                .setTitle(NoticeTitle)
                .save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
//                            Toast.makeText(NoticeAddActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
