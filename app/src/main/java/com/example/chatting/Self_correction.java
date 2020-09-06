package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;

public class Self_correction extends AppCompatActivity {
    private ImageView mIvBack;
    private Button sure;
    private EditText context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_correction);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        mIvBack = (ImageView)findViewById(R.id.im_back);
        sure=findViewById(R.id.sure);
        context=findViewById(R.id.context);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                String mycontext=context.getText().toString();
                if(!mycontext.isEmpty())
                {
                    intent.putExtra("context_return",mycontext);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
        DrawableUtil drawableUtil = new DrawableUtil(context, new DrawableUtil.OnDrawableListener() {

            @Override
            public void onLeft(View v, Drawable left) {

            }

            @Override
            public void onRight(View v, Drawable right) {
                if (context.length() > 1){
                    context.setText("");
                }
            }
        });

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}