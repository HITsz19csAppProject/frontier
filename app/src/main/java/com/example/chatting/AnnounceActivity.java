package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import AdaptObject.news;
import Adapter.NewsAdapter;
import Bean.MessageItem;
import Tools.ServerTools;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
                get();
            }
        });

    }

    public void get(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<MessageItem> query=new BmobQuery<MessageItem>();
                query.findObjects(new FindListener<MessageItem>() {
                    @Override
                    public void done(List<MessageItem> list, BmobException e) {
                        List<MessageItem> list1 =new ArrayList<>();
                        if(list1!=null){
                            System.out.println("成功"+list.get(0).getTitle()+list.get(0).getContent());
                            final String[] title =new String[list1.size()];
                            final String[] content=new String[list1.size()];
                            for(int i=0;i<list1.size();i++){
                                title[i]=list1.get(i).getTitle();
                                content[i]=list1.get(i).getContent();
                            }

                           // class NoticeAdapter extends
                        }
                    }
                });
            }
        });

    }
}
