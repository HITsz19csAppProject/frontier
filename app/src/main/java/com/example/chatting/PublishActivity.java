package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import Adapter.MyAdapter;
import Adapter.NewsAdapter;
import AdaptObject.news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Bean.MessageItem;
import Bean.User;
import Tools.ServerTools;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import static com.example.chatting.MyApplication.CurrentUser;

/**
 * 我已发出界面
 */
public class PublishActivity extends AppCompatActivity {

    private NewsAdapter adapter1;
    private List<news> newsList = new ArrayList<>();
    private ImageView mIvBack;
    private Button add;
    private SwipeRefreshLayout flush;
    private ListView listView;
    private SwipeRefreshLayout.OnRefreshListener listener;
    private int minInstance;
    private float mFirstY;
    private float mCurrentY;
    private int status;
    private boolean mShow = true;
    private ObjectAnimator animator;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mIvBack = (ImageView) findViewById(R.id.im_back);
        add = findViewById(R.id.my_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublishActivity.this, PostActivity.class);
                new ServerTools().MessageShow(newsList);
                startActivityForResult(intent, 1);
            }
        });
        flush = findViewById(R.id.flush);
        initSwipeRefresh();
        AutoRefresh();
        listener.onRefresh();
        flush.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        get();
                        flush.setRefreshing(false);
                        //添加数据设置适配器

                    }
                }, 20);
            }
        });

        adapter1 = new NewsAdapter(PublishActivity.this, R.layout.news, newsList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        //1.给listview增加HeaderView
        //2.获取系统认为的最低滑动距离
        minInstance = ViewConfiguration.get(this).getScaledTouchSlop();
        //3.判断滑动事件
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if (mCurrentY - mFirstY > minInstance) {
                            status = 0;  // 向下
                        } else {
                            status = 1;  // 向上
                        }
                        if (status == 1) {
                            if (mShow) {
                                barAnim(1);  // 隐藏
                                mShow = false;
                            }
                        } else {
                            if (!mShow) {
                                barAnim(0); // 显示
                                mShow = true;
                            }
                        }
                        break;
                }
                return false;
            }
        });
        
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

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initNews();

    }

    public void AutoRefresh()
    {
        flush.post(new Runnable() {
            @Override
            public void run () {
                flush.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        get();
                        flush.setRefreshing(false);
                    }
                },500);
            }

        });
        listener.onRefresh();
    }

    public void initNews() {
        new ServerTools().MessageShow(newsList);
    }
    
    private void barAnim(int i) {
        if (animator != null && animator.isRunning())
            animator.cancel();
        if (i == 0) {
            animator = ObjectAnimator.ofFloat(
                    add,
                    "translationY",
                    add.getTranslationY(),
                    0);
        }else{
            animator = ObjectAnimator.ofFloat(
                    add,
                    "translationY",
                    add.getTranslationY(),
                    -add.getHeight());
        }
        animator.start();
    }
    private void initSwipeRefresh() {
         listener = new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        get();
                        flush.setRefreshing(false);
                        //添加数据设置适配器
                    }
                }, 20);
            }
        };
        flush.setOnRefreshListener(listener);
    }

        public void get(){
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    BmobQuery<MessageItem> query = new BmobQuery<MessageItem>();
                    query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
                    query.order("-updatedAt");
                    query.include("author");
                    query.findObjects(new FindListener<MessageItem>() {
                        @Override
                        public void done(List<MessageItem> list, BmobException e) {
                            List<MessageItem> lists = new ArrayList<>();
                            if (list != null) {
                                System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                                final String[] title = new String[list.size()];
                                final String[] content = new String[list.size()];
                                final String[] author = new String[list.size()];

                                for (int i = 0; i < list.size(); i++) {
                                    title[i] = list.get(i).getTitle();
                                    content[i] = list.get(i).getContent();
                                    author[i] = list.get(i).getAuthor().getName();
                                }
                                listView.setAdapter(new MyAdapter(getApplication(), title, content, author));
                            }
                        }
                    });
                }
            }); //声明一个子线程
            thread.start();
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    initSwipeRefresh();
                    AutoRefresh();
                }
                break;
            default:
                break;
        }
    }
    }


