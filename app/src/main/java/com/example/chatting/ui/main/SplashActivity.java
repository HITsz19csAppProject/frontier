package com.example.chatting.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.chatting.LoginActivity;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
