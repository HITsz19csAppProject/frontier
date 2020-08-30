package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import Adapter.NewsAdapter;
import AdaptObject.news;

import java.util.ArrayList;
import java.util.List;

import Bean.MessageItem;
import Bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import static com.example.chatting.MyApplication.CurrentUser;

public class PublishActivity extends AppCompatActivity {

    private NewsAdapter adapter1;
    private List<news> newsList=new ArrayList<>();
    private ImageView mIvBack;
    private Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        mIvBack = (ImageView)findViewById(R.id.im_back);
        add=findViewById(R.id.my_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublishActivity.this, PostActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initNews();
        adapter1 = new NewsAdapter(PublishActivity.this, R.layout.news, newsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                news news = newsList.get(position);
                Intent intent = new Intent(PublishActivity.this, NewsActivity.class);
                String news_headline = news.getHeadline();
                String news_receiver = news.getWriter();
                String news_context = news.getContext();
                intent.putExtra("extra_headline", news_headline);
                intent.putExtra("extra_writer", news_receiver);
                intent.putExtra("extra_context", news_context);
                startActivity(intent);
            }
        });
    }

    private void initNews() {
//        news N1=new news("年纪大会","致全年级同学","本年级大会将在7月21号举行");
//        news N2=new news("体能测试","致全年级同学","体能测试全年级参加");
//        news N3=new news("文理通识选课","致全年级同学","文理通识选课，同学们自发在网上选择");
//        news N4=new news("辅修双学位","致全年级同学","意向辅修双学位的同学请在线上报名");
//        news N5=new news("体育选课","致全年级同学","体育选课将在7月21日下午一点正式开始");
//
//        newsList.add(N1);
//        newsList.add(N2);
//        newsList.add(N3);
//        newsList.add(N4);
//        newsList.add(N5);
        if (BmobUser.isLogin()) {
            BmobQuery<MessageItem> q = new BmobQuery<>();
            q.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            q.order("-updatedAt");

            q.include("author");
            q.findObjects(new FindListener<MessageItem>() {
                //todo
                @Override
                public void done(List<MessageItem> list, BmobException e) {
                    if (e == null) {
                        for (int i = 0; i<list.size(); i++) {
                            MessageItem newMessage = list.get(i);
                            System.out.println(newMessage.getContent());
                            newsList.add(new news(newMessage.getTitle(), CurrentUser.getUsername(), newMessage.getContent()));
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String myheadline = data.getStringExtra("headline_return");
                    String mycontext = data.getStringExtra("context_return");
                    news M = new news(myheadline, "致全年级同学", mycontext);
                    newsList.add(M);
                    refresh(adapter1);
                }
                break;
            default:
                break;
        }
    }

    public void refresh(NewsAdapter adapter)
    {
        adapter.notifyDataSetChanged();
    }

}

