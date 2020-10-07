package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class PublishActivity extends AppCompatActivity {

    private NewsAdapter adapter1;
    private List<news> newsList = new ArrayList<>();
    private ImageView mIvBack;
    private Button add;
    private SwipeRefreshLayout flush;
    private ListView listView;
    private SwipeRefreshLayout.OnRefreshListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ActionBar actionBar = getSupportActionBar();
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
        listView = (ListView) findViewById(R.id.list_view);
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

                                for (int i = 0; i < list.size(); i++) {
                                    title[i] = list.get(i).getTitle();
                                    content[i] = list.get(i).getContent();
                                }
                                class MyAdapter extends BaseAdapter {
                                    private Context context;

                                    public MyAdapter(Context context) {
                                        this.context = context;
                                    }

                                    @Override
                                    public int getCount() {
                                        return title.length;
                                    }

                                    @Override
                                    public Object getItem(int position) {
                                        return title[position];
                                    }

                                    @Override
                                    public long getItemId(int position) {
                                        return position;
                                    }

                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        ViewHolder viewHolder;
                                        if (convertView == null) {
                                            LayoutInflater inflater = LayoutInflater.from(context);
                                            convertView = inflater.inflate(R.layout.activity_news, null);//实例化一个布局文件
                                            viewHolder = new ViewHolder();
                                            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.News_headline);
                                            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.News_context);
                                            convertView.setTag(viewHolder);

                                        } else {
                                            viewHolder = (ViewHolder) convertView.getTag();
                                        }

                                        viewHolder.tv_title.setText(title[position]);
                                        viewHolder.tv_content.setText(content[position]);
                                        return convertView;
                                    }

                                    class ViewHolder {
                                        TextView tv_title;
                                        TextView tv_content;
                                    }
                                }
                                listView.setAdapter(new MyAdapter(getApplication()));
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


