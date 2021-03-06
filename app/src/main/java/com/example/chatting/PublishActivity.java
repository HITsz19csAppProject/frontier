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

import Adapter.CommunityItemAdapter;
import Adapter.MessageItemAdapter;
import Adapter.NewsAdapter;
import AdaptObject.news;

import java.util.ArrayList;
import java.util.List;

import Bean.CommunityItem;
import Bean.MessageItem;
import Bean.User;
import Tools.ServerTools;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

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
    private MessageItemAdapter mAdapter;

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
        flush = findViewById(R.id.flush);
        listView = (ListView) findViewById(R.id.list_view);
        adapter1 = new NewsAdapter(PublishActivity.this, R.layout.news, newsList);
        mAdapter = new MessageItemAdapter(PublishActivity.this, new ArrayList<MessageItem>());
        listView.setAdapter(mAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublishActivity.this, PostActivity.class);
                new ServerTools().MessageShow(newsList);
                startActivityForResult(intent, 1);
            }
        });

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

//        listView.setAdapter(adapter1);
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
                                //barAnim(1);  // 隐藏
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

    private void barAnim(int i) {
        if (animator != null && animator.isRunning())
            animator.cancel();
        if (i == 0) {
            animator = ObjectAnimator.ofFloat(
                    add,
                    "translationY",
                    add.getTranslationY(),
                    0);
        } else {
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

    private void get() {
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
                        if (list != null) {
                            System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                            mAdapter.setData(list);
                            new ServerTools().BeforeDownLoadMessage(PublishActivity.this, list);
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
                return true;
            case 1:
                BmobQuery<MessageItem> query = new BmobQuery<MessageItem>();
                query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
                query.addWhereEqualTo("title", newsList.get(position).getHeadline());
                query.addWhereEqualTo("content", newsList.get(position).getContext());
                query.include("author");
                query.findObjects(new FindListener<MessageItem>() {
                    @Override
                    public void done(List<MessageItem> list, BmobException e) {
                        if (list != null) {
                            System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                            MessageItem messageItem = new MessageItem();
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


