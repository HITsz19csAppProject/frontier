package com.example.chatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import Adapter.CommunityItemAdapter;
import Adapter.MessageItemAdapter;
import Adapter.NewsAdapter;
import AdaptObject.news;

import java.util.ArrayList;
import java.util.List;

import Bean.CommunityItem;
import Bean.MessageItem;
import Bean.User;
import Tools.GraphTools;
import Tools.ServerTools;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 我已发出界面
 */
public class Mypublish extends AppCompatActivity {

    private NewsAdapter adapter1;
    private List<news> newsList = new ArrayList<>();
    private ImageView mIvBack;
    private SwipeRefreshLayout flush;
    private ListView listView;
    private SwipeRefreshLayout.OnRefreshListener listener;
    private boolean mShow = true;
    private ObjectAnimator animator;
    private CommunityItemAdapter mAdapter;
    private Button save;
    private String myHeadline;
    private String myContext;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announced);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mIvBack = (ImageView) findViewById(R.id.im_back);
        flush = findViewById(R.id.flush111);
        listView = (ListView) findViewById(R.id.list_view111);
        adapter1 = new NewsAdapter(Mypublish.this, R.layout.news, newsList);
        mAdapter = new CommunityItemAdapter(Mypublish.this, new ArrayList<CommunityItem>());
        listView.setAdapter(mAdapter);
        save=findViewById(R.id.btn_save_pop);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                news news = newsList.get(position);
                Intent intent = new Intent(Mypublish.this, NewsActivity.class);
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

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle("选择操作");
                contextMenu.add(0,0,0,"修改");
                contextMenu.add(0,1,0,"删除");
            }
        });

    }


    private void AutoRefresh() {
        flush.post(new Runnable() {
            @Override
            public void run() {
                flush.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        get();
                        flush.setRefreshing(false);
                    }
                }, 500);
            }

        });
        listener.onRefresh();
    }

    private void initNews() {
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

    private void get() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<CommunityItem> query = new BmobQuery<CommunityItem>();
                query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
                query.order("-updatedAt");
                query.include("author");
                query.findObjects(new FindListener<CommunityItem>() {
                    @Override
                    public void done(List<CommunityItem> list, BmobException e) {
                        if (list != null) {
                            System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                            mAdapter.setData(list);
                            new ServerTools().BeforeDownLoadCommunityMessage(Mypublish.this, list);
                        }
                    }
                });
            }
        }); //声明一个子线程
        thread.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case 0:
                Userdiolog diolog=new Userdiolog(this);
//
//                BmobQuery<MessageItem> query = new BmobQuery<MessageItem>();
//
//                query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
//                query.addWhereEqualTo("title", newsList.get(position).getHeadline());
//                query.addWhereEqualTo("content", newsList.get(position).getContext());
//
//                diolog.text_title.setText(newsList.get(0).getHeadline());
//                diolog.text_content.setText(newsList.get(0).getContext());
                diolog.show();

//                query.findObjects(new FindListener<MessageItem>() {
//                    @Override
//                    public void done(List<MessageItem> list, BmobException e) {
//                        save.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                MessageItem messageItem = new MessageItem();
//
//                                messageItem.setObjectId(list.get(0).getObjectId());
//                                messageItem.setTitle(diolog.text_title.toString());
//                                messageItem.setContent(diolog.text_content.toString());
//
//                                messageItem.update(new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e == null) {
//                                            Toast.makeText(Mypublish.this, "更新数据成功", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(Mypublish.this, "更新数据失败返回Exception为:" + e, Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                        }
//                    });
//                    }
//                });
                return true;

            case 1:
                BmobQuery<CommunityItem> query1 = new BmobQuery<CommunityItem>();
                query1.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
                query1.addWhereEqualTo("title", newsList.get(position).getHeadline());
                query1.addWhereEqualTo("content", newsList.get(position).getContext());
                query1.include("author");
                query1.findObjects(new FindListener<CommunityItem>() {
                    @Override
                    public void done(List<CommunityItem> list, BmobException e) {
                        if (list != null) {
                            System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                            CommunityItem messageItem = new CommunityItem();
                            messageItem.setObjectId(list.get(0).getObjectId());
                            messageItem.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        System.out.println("删除成功");
                                    }
                                    else
                                    {
                                        System.out.println("删除失败");
                                    }
                                }
                            });

                        }


                    }

                });
                return true;
        }

        return true;
    }

}

