package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import Tools.ServerTools;

import static com.example.chatting.MyApplication.CurrentUser;
import static com.example.chatting.MyApplication.UserInfo;

public class LoginActivity extends AppCompatActivity {
    private TextView register_user;
    private TextView register_password;
    private Button register_ok;
    private CheckBox mCbShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        addControl();//加载控件
        mCbShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                //如果选中，显示密码
                register_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                //否则隐藏密码
                register_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        onClick onClick = new onClick();
        register_ok = (Button) findViewById(R.id.btn_login);
        register_ok.setOnClickListener(onClick);
    }


    private void Login() {
        //Bmob登陆方法
        String usr = register_user.getText().toString().trim();
        String pwd = register_password.getText().toString().trim();
        CurrentUser.setAll(usr, pwd);
        UserInfo.edit().putString(usr, pwd).apply();

        new ServerTools().UserLogin(LoginActivity.this);
    }
    private class onClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    Login();
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

