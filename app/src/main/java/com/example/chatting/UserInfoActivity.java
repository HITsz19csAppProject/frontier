package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatting.R;

import AdaptObject.news;
import Bean.User;
import cn.bmob.v3.BmobUser;

import static com.example.chatting.MyApplication.CurrentUser;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mStuId, mMajor, mGrade, mtv_user_info_class;
    private TextView tv_user_info_name,tv_user_info_sex,tv_user_info_stuno,tv_user_info_tel,tv_user_info_yuan,tv_user_info_class,tv_user_info_created;
    String mycontext;
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
        tv_user_info_name=findViewById(R.id.tv_user_info_name);
        tv_user_info_sex=findViewById(R.id.tv_user_info_sex);
        tv_user_info_stuno=findViewById(R.id.tv_user_info_stuno);
        tv_user_info_tel=findViewById(R.id.tv_user_info_tel);
        tv_user_info_yuan=findViewById(R.id.tv_user_info_yuan);
        tv_user_info_class=findViewById(R.id.tv_user_info_class);
        tv_user_info_created=findViewById(R.id.tv_user_info_created);
        CurrentUser = BmobUser.getCurrentUser(User.class);

        tv_user_info_stuno.setText(CurrentUser.getUsername());
        tv_user_info_yuan.setText(CurrentUser.getMajor());
        tv_user_info_class.setText(CurrentUser.getMajor());
        tv_user_info_created.setText(CurrentUser.getGrade()+"级");

        tv_user_info_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, Self_correction.class);
                startActivityForResult(intent,1);
            }
        });
        tv_user_info_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, Self_correction.class);
                startActivityForResult(intent,2);
            }
        });
        tv_user_info_stuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, Self_correction.class);
                startActivityForResult(intent,3);
            }
        });
        tv_user_info_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, Self_correction.class);
                startActivityForResult(intent,4);
            }
        });
        tv_user_info_yuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, Self_correction.class);
                startActivityForResult(intent,5);
            }
        });
        tv_user_info_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, Self_correction.class);
                startActivityForResult(intent,6);
            }
        });
        tv_user_info_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, Self_correction.class);
                startActivityForResult(intent,7);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                     mycontext = data.getStringExtra("context_return");
                     tv_user_info_name.setText(mycontext);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    mycontext = data.getStringExtra("context_return");
                    tv_user_info_sex.setText(mycontext);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    mycontext = data.getStringExtra("context_return");
                    tv_user_info_stuno.setText(mycontext);
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    mycontext = data.getStringExtra("context_return");
                    tv_user_info_tel.setText(mycontext);
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    mycontext = data.getStringExtra("context_return");
                    tv_user_info_yuan.setText(mycontext);
                }
                break;
            case 6:
                if (resultCode == RESULT_OK) {
                    mycontext = data.getStringExtra("context_return");
                    tv_user_info_class.setText(mycontext);
                }
                break;
            case 7:
                if (resultCode == RESULT_OK) {
                    mycontext = data.getStringExtra("context_return");
                    tv_user_info_created.setText(mycontext);
                }
                break;
            default:
                break;
        }
    }
}

