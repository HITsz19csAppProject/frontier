package com.example.chatting.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatting.R;

import Bean.User;
import cn.bmob.v3.BmobUser;

import static com.example.chatting.MyApplication.CurrentUser;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mStuId, mMajor, mGrade, mtv_user_info_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        mIvBack = (ImageView)findViewById(R.id.im_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mStuId = findViewById(R.id.tv_user_info_stuno);
        mMajor = findViewById(R.id.tv_user_info_yuan);
        mGrade = findViewById(R.id.tv_user_info_created);
        mtv_user_info_class = findViewById(R.id.tv_user_info_class);
        CurrentUser = BmobUser.getCurrentUser(User.class);

        mStuId.setText(CurrentUser.getUsername());
        mMajor.setText(CurrentUser.getMajor());
        mtv_user_info_class.setText(CurrentUser.getMajor());
        mGrade.setText(CurrentUser.getGrade()+"级");
    }
}

