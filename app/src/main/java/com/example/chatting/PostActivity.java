package com.example.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import Bean.MessageItem;
import Tools.ServerTools;

public class PostActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button mBtnAddLabel;
    private ImageView mIvBack;
    private Button publish;
    private EditText my_headline;
    private EditText my_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("进入PostActivity活动");
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
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

        imageView = (ImageView)findViewById(R.id.im_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        publish=findViewById(R.id.publish);
        my_headline=findViewById(R.id.my_headline);
        my_context=findViewById(R.id.my_context);
        publish.setOnClickListener(view -> {
            Intent intent=new Intent();
            String myheadline=my_headline.getText().toString();
            String mycontext=my_context.getText().toString();
            if(!myheadline.isEmpty()&&!mycontext.isEmpty())
            {
                System.out.println("获取成功");
                MessageItem newMessage = new MessageItem();
                newMessage.setTitle(myheadline);
                newMessage.setContent(mycontext);
                new ServerTools().SaveMessage(PostActivity.this, newMessage);

                intent.putExtra("headline_return", myheadline);
                intent.putExtra("context_return", mycontext);
                setResult(RESULT_OK, intent);
                finish();
            }
            else{
                System.out.println("获取失败");
                my_headline.setText("error");
            }
        });
        mBtnAddLabel = (Button)findViewById(R.id.btn_addlabel);
        mBtnAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, LabelActivity.class);
                startActivity(intent);
            }
        });
    }
}