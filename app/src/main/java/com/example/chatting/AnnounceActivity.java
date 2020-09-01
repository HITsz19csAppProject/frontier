package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import Bean.MessageItem;
import Tools.ServerTools;

public class AnnounceActivity extends AppCompatActivity {

    private Button publish,addLabel;
    private EditText my_headline;
    private EditText my_context;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            public void onClick(View view)
            {
                onBackPressed();
            }
        });  //返回

        publish=findViewById(R.id.publish);
        addLabel = findViewById(R.id.btn_addlabel);
        my_headline=findViewById(R.id.my_headline);
        my_context=findViewById(R.id.my_context);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("已经点击");
                Intent intent1=new Intent();
                String myheadline=my_headline.getText().toString();
                String mycontext=my_context.getText().toString();
                if(!myheadline.isEmpty()&&!mycontext.isEmpty())
                {
                    System.out.println("获取成功");
                    MessageItem newMessage = new MessageItem();
                    newMessage.setTitle(myheadline);
                    newMessage.setContent(mycontext);
                    new ServerTools().SaveMessage(AnnounceActivity.this, newMessage);

                    intent1.putExtra("headline_return",myheadline);
                    intent1.putExtra("context_return",mycontext);
                    setResult(RESULT_OK,intent1);
                    finish();
                }
                else{
                    System.out.println("获取失败");
                    my_headline.setText("error");
                }
            }
        });
        //标签的添加
        addLabel.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnounceActivity.this,LabelActivity.class);
                startActivity(intent);
            }
        });

    }
}
