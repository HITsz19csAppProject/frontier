package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<range> rangeList = new ArrayList<>();
    private List<news> newsList=new ArrayList<>();
    private Button add;
    private Button notice;
    private Button myself;
    private NewsAdapter adapter1;
    private long exitTime = 0;
    private Handler mHandler = new Handler();
    private Runnable mFinish = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        notice=findViewById(R.id.notice);
        myself=findViewById(R.id.myself);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ForthActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FifthActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        initRange();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        RangeAdapter adapter = new RangeAdapter(rangeList);
        recyclerView.setAdapter(adapter);

        initNews();
        adapter1 = new NewsAdapter(MainActivity.this, R.layout.news, newsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                news news = newsList.get(position);
                Intent intent = new Intent(MainActivity.this, secondActivity.class);
                String news_headline = news.getHeadline();
                String news_writer = news.getWriter();
                String news_context = news.getContext();
                intent.putExtra("extra_headline", news_headline);
                intent.putExtra("extra_writer", news_writer);
                intent.putExtra("extra_context", news_context);
                startActivity(intent);
            }
        });
    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK) {
                        String myheadline = data.getStringExtra("headline_return");
                        String mycontext = data.getStringExtra("context_return");
                        news M = new news(myheadline, "me", mycontext);
                        newsList.add(M);
                        refresh(adapter1);
                    }
                    break;
                default:
                    break;
            }
        }



    private void initNews() {
        for(int i=0;i<2;i++)
        {
            news N1=new news("Apple","John","I love apples");
            news N2=new news("Grape","JoJo","I love grapes");
            news N3=new news("Tomato","Lisa","I love tomatoes");
            news N4=new news("Cherry","Lina","I love cherry");
            news N5=new news("Strawberry","Jack","I love strawberries");
            news N6=new news("Pear","Angel","I love pear");

            newsList.add(N1);
            newsList.add(N2);
            newsList.add(N3);
            newsList.add(N4);
            newsList.add(N5);
            newsList.add(N6);
        }
    }

    private void initRange() {
        for(int i=0;i<1;i++)
        {
            range time = new range("时间");
            range study=new range("学习");
            range talk=new range("闲聊");
            range sports=new range("运动");
            range games=new range("游戏");
            range songs=new range("唱歌");
            range poem=new range("诗歌");
            range life=new range("生活");

            rangeList.add(time);
            rangeList.add(study);
            rangeList.add(talk);
            rangeList.add(sports);
            rangeList.add(games);
            rangeList.add(songs);
            rangeList.add(poem);
            rangeList.add(life);
        }
    }

    public void refresh(NewsAdapter adapter)
    {
        adapter.notifyDataSetChanged();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((System.currentTimeMillis()-exitTime) > 2000){
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            mHandler.postDelayed(mFinish, 0);
        }
        //return super.onKeyDown(keyCode, event);
        return false;
    }
}
