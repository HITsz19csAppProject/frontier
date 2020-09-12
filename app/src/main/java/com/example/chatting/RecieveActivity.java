package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapter.NewsAdapter;
import AdaptObject.news;
import Bean.MessageItem;
import Bean.User;
import Tools.ServerTools;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import static com.example.chatting.MyApplication.CurrentUser;

public class RecieveActivity extends AppCompatActivity {
    private NewsAdapter adapter1;
    private List<news> newsList = new ArrayList<>();
    private ImageView mIvBack;
    private ListView listView;
    private Button renew;

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
        new ServerTools().MessageShow(newsList);

        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMessage();
            }
        });

        Intent intent = new Intent(RecieveActivity.this, NewsActivity.class);
    }


        public void getMessage () {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    BmobQuery<MessageItem> query = new BmobQuery<MessageItem>();
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
    }


