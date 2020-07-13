package com.example.chatting.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.chatting.MainActivity;
import com.example.chatting.R;

import static androidx.core.content.ContextCompat.startActivity;

public class LoginActivity extends AppCompatActivity {
    private Button mBtnLogin;
    private EditText mEtPassword;
    private CheckBox mCbShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mEtPassword = (EditText)findViewById(R.id.et_password);
        mCbShow = (CheckBox)findViewById(R.id.checkBox1);
        mCbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
                finish();
            }
        });
    }



    }


