package com.example.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.Bean.User;
import com.example.chatting.MainActivity;
import com.example.chatting.R;
import com.example.backend.JwLoginActivity;

import java.io.IOException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends FragmentActivity{
    private TextView register_user;
    private TextView register_password;
    private Button register_ok;
    private User  user;
    private CheckBox mCbShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "0c073307ca22bf3e1268f1f70bef2941");
        addControl();//加载控件
        addRegisterShow();//注册方法

        mCbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    register_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    register_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });
    }
    //Bmob的注册方法
    private void addRegisterShow() {
        final BmobUser user = new BmobUser();
                boolean ans = true;
                user.setUsername(register_user.getText().toString());
                user.setPassword(register_password.getText().toString());
                //插入方法
//                try {
//                    ans = new JwLoginActivity().LoginMain(register_password.getText().toString(),user.getUsername());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                if(ans) {
                    user.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                //判断是否注册成功成功则跳转到登陆的页面
                                Intent intent_register = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent_register);
                                Toast.makeText(RegisterActivity.this, "添加数据成功，返回objectId为：" + user.getObjectId(), Toast.LENGTH_SHORT).show();
                                //这里我们需要了解一下什么objectid就像是一个登陆校验一样只要有这个就代表着你往表里面写入的数据是成功的了。
                            } else {
                                Toast.makeText(RegisterActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
    }

    private void addControl() {
        register_user = (TextView) findViewById(R.id.et_username);
        register_password = (TextView) findViewById(R.id.et_password);
        register_ok = (Button) findViewById(R.id.btn_login);
        mCbShow = (CheckBox)findViewById(R.id.checkBox1);


    }
}
