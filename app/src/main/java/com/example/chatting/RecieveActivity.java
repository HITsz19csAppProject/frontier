package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
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
    private List<news> newsList = new ArrayList<>();
    private ImageView mIvBack;
    private SwipeRefreshLayout refresh;
    private SwipeRefreshLayout.OnRefreshListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mIvBack = (ImageView) findViewById(R.id.im_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       // initNews();
        adapter1 = new NewsAdapter(RecieveActivity.this, R.layout.news, newsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        new ServerTools().MessageShow(newsList);
        refresh = findViewById(R.id.refresh);
        initSwipeRefresh();
        refresh.post(new Runnable() {
            @Override
            public void run () {
                refresh.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter1.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                    }
                },500);
            }

        });
        listener.onRefresh();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        adapter1.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                    }
                }, 20);
            }
        });
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

    private void initSwipeRefresh() {
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        adapter1.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                        //添加数据设置适配器
                    }
                }, 20);
            }
        };
        refresh.setOnRefreshListener(listener);
    }
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


