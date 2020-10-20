package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import AdaptObject.ReceivedNews;
import Adapter.ReceiveAdapter;
import Bean.MessageItem;
import Bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ReceiveActivity extends AppCompatActivity {
    private List<ReceivedNews> newsList = new ArrayList<>();
    private ImageView mIvBack;
    private ListView listView;
    private Button renew;
    private MessageDataBaseUtils utils ; //数据库操作
    private ReceiveAdapter adapter;
    private List<ReceivedNews> lists;
    public String[] isRead ;

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

        renew = (Button) findViewById(R.id._renew);
        listView = (ListView) findViewById(R.id.list_view);
        //new ServerTools().MessageShow(newsList);

        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMessage();
            }
        });

        utils = new MessageDataBaseUtils(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReceivedNews news = lists.get(i);
                String title = news.getHeadline();
                String writer = news.getWriter();
                String content = news.getContext();
                if(!utils.searchMessageIsRead(title)){
                    utils.setMessageIsRead(title);
                    isRead[i] = "1";
                    news.setIsRead("已读");
                    lists.set(i,news);
                    adapter.notifyDataSetChanged();
                }
                Intent intent =new Intent(ReceiveActivity.this,NewsActivity.class);
                intent.putExtra("extra_headline",title);
                intent.putExtra("extra_writer",writer);
                intent.putExtra("extra_context",content);
                startActivity(intent);
            }
        });

    }


    public void getMessage () {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                BmobQuery<MessageItem> query = new BmobQuery<MessageItem>();
                query.addWhereNotEqualTo("author", BmobUser.getCurrentUser(User.class));
                query.order("-updatedAt");
                //包含作者信息
                query.include("author");
                query.findObjects(new FindListener<MessageItem>() {
                    @Override
                    public void done(List<MessageItem> list, BmobException e) {
                        lists = new ArrayList<>();
                        if (list != null) {
                            System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                            final String[] title = new String[list.size()];
                            final String[] content = new String[list.size()];
                            final String[] author = new String[list.size()];
                            isRead = new String[list.size()];

                            for (int i = 0; i < list.size(); i++) {
                                title[i] = list.get(i).getTitle();
                                content[i] = list.get(i).getContent();
                                author[i]=list.get(i).getAuthor().getName();
                                if(!utils.searchMessage(title[i])){
                                    utils.addMessage(title[i],"0");
                                }
                                else{
                                    if(utils.searchMessageIsRead(title[i])){
                                        isRead[i] = "1";
                                    }
                                    else{
                                        isRead[i] = "0";
                                    }
                                }
                                ReceivedNews news = new ReceivedNews(title[i],author[i],content[i],isRead[i]);
                                lists.add(news);
                            }
                            adapter = new ReceiveAdapter(getApplication(), title, content,author,isRead);
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        }); //声明一个子线程
        thread.start();
    }
}



