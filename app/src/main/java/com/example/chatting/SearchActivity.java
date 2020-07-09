package com.example.chatting;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatting.R;
import com.example.chatting.DrawableUtil;

public class SearchActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mEditText = (EditText) findViewById(R.id.et_search);
        DrawableUtil drawableUtil = new DrawableUtil(mEditText, new DrawableUtil.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {
//                Intent intent = new Intent(SearchActivity.this, HomeFragment.class);
//                startActivity(intent);
                onBackPressed();
            }

            @Override
            public void onRight(View v, Drawable right) {
                if (mEditText.length() > 1){
                    mEditText.setText("");
                }
            }
        });
    }

}
