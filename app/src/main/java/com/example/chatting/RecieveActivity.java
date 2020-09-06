package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.NewsAdapter;
import AdaptObject.news;
import Tools.ServerTools;

public class RecieveActivity extends AppCompatActivity {
    private NewsAdapter adapter1;
    private List<news> newsList=new ArrayList<>();
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
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
//        initNews();
        adapter1 = new NewsAdapter(RecieveActivity.this, R.layout.news, newsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        new ServerTools().MessageShow(newsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                news news = newsList.get(position);
                Intent intent = new Intent(RecieveActivity.this, NewsActivity.class);
                String news_headline = news.getHeadline();
                String news_writer = news.getWriter();
                String news_context = news.getContext();
                intent.putExtra("extra_headline", news_headline);
                intent.putExtra("extra_writer", news_writer);
                intent.putExtra("extra_context", news_context);
                new ServerTools().MessageShow(newsList);
                startActivity(intent);
            }
        });
    }


    private void initNews() {
        news N1=new news("年纪大会","辛欣","本年级大会将在7月21号举行");
        news N2=new news("体能测试","刘老师","体能测试全年级参加");
        news N3=new news("文理通识选课","吴老师","文理通识选课，同学们自发在网上选择");
        news N4=new news("辅修双学位","教学秘书","意向辅修双学位的同学请在线上报名");
        news N5=new news("体育选课","辛欣","体育选课将在7月21日下午一点正式开始");

        newsList.add(N1);
        newsList.add(N2);
        newsList.add(N3);
        newsList.add(N4);
        newsList.add(N5);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    String myheadline = data.getStringExtra("headline_return");
//                    String mycontext = data.getStringExtra("context_return");
//                    news M = new news(myheadline, "me", mycontext);
//                    newsList.add(M);
//                    refresh(adapter1);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void refresh(NewsAdapter adapter)
//    {
//        adapter.notifyDataSetChanged();
//    }
    }

