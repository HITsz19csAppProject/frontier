package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Login.TempLoginActivity;
import com.example.Bean.User;

import org.apache.log4j.Category;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {
    private TextView register_user;
    private TextView register_password;
    private Button register_ok;
    private User user;
    private CheckBox mCbShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "0c073307ca22bf3e1268f1f70bef2941");
        addControl();//加载控件
        register_user = (TextView) findViewById(R.id.et_username);
        register_password = (TextView) findViewById(R.id.et_password);
        mCbShow = (CheckBox)findViewById(R.id.checkBox1);
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
        onClick onClick = new onClick();
        register_ok = (Button) findViewById(R.id.btn_login);
        register_ok.setOnClickListener(onClick);
    }


    private void addLogin() {
        //Bmob登陆方法
        final BmobUser user = new BmobUser();
        String lgU = register_user.getText().toString().trim();
        String lgp = register_password.getText().toString().trim();
        user.setUsername(lgU);
        user.setPassword(lgp);
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    //登陆成功就跳转到主页面
                    Intent in_success = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(in_success);
                }
                else {//如果登陆不成功，查询该账号是否注册
                    BmobQuery<Category> categoryBmobQuery = new BmobQuery<>();
                    categoryBmobQuery.addWhereEqualTo("username", user.getUsername());
                    categoryBmobQuery.findObjects(new FindListener<Category>() {
                    @Override
                    public void done(List<Category> object, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            Intent intent_LoginWRONG = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(intent_LoginWRONG);
                        }
                        else {//尝试进行注册
                            final boolean[] ans = {false};
                            //插入方法
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        User tempUser = new User();
                                        tempUser.setName(lgU);
                                        tempUser.setPassword(lgp);
                                        try {
                                            //使用了上一届学长的代码，完成了校园网认证的功能。（摊手
                                            ans[0] = new TempLoginActivity().Login(tempUser);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                            });
                            thread.start();
                            try {
                                    thread.join();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            if(ans[0]) {
                                user.signUp(new SaveListener<BmobUser>() {
                                    @Override
                                    public void done(BmobUser bmobUser, BmobException e) {
                                        if (e == null) {
                                            //判断是否注册成功成功则跳转到登陆的页面
                                            Intent intent_register = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent_register);
                                            Toast.makeText(LoginActivity.this, "添加数据成功，返回objectId为：" + user.getObjectId(), Toast.LENGTH_SHORT).show();
                                            //这里我们需要了解一下什么objectid就像是一个登陆校验一样只要有这个就代表着你往表里面写入的数据是成功的了。
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                    });
                        /**Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                         startActivity(intent);*/
                }
            }
        });
    }
     private class onClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    addLogin();
            }
        }
    }
     private void addControl() {
         register_user = (TextView) findViewById(R.id.et_username);
         register_password = (TextView) findViewById(R.id.et_password);
         register_ok = (Button) findViewById(R.id.btn_login);
         mCbShow = (CheckBox)findViewById(R.id.checkBox1);
     }

}

