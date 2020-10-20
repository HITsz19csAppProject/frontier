package com.example.chatting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import AdaptObject.LabelModel;
import AdaptObject.post;
import AdaptObject.range;
import AdaptObject.range_pic;
import Adapter.MyAdapter;
import Adapter.PostAdapter;
import Adapter.RangeAdapter;
import Adapter.Range_PicAdapter;
import Bean.CommunityItem;
import Bean.User;
import Tools.PostTools;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.app.Activity.RESULT_OK;

public class ChattingFragment extends Fragment {

    private ChattingViewModel chattingViewModel;
    private List<range> rangeList = new ArrayList<>();
    private List<range_pic> range_pics=new ArrayList<>();
    private List<post> newsList = new ArrayList<>();
    private Button add;
    private EditText mTvSearch;
    private PostAdapter adapter1;
    private Button sum;
    public DrawerLayout drawerLayout;
    private SwipeRefreshLayout refresh;
    private ListView listView;
    private ListView range_piclist;
    private SwipeRefreshLayout.OnRefreshListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chattingViewModel =
                ViewModelProviders.of(this).get(ChattingViewModel.class);
        View root = inflater.inflate(R.layout.chatting_fragment, container, false);

        add = root.findViewById(R.id.add);
        sum = root.findViewById(R.id.sum);
        drawerLayout = (DrawerLayout) root.findViewById(R.id.drawer_layout_home);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnnounceActivity.class);
                new PostTools().CommunityMessageShow(newsList);
                startActivityForResult(intent, 1);
            }
        });
        initRange_pic();
        Range_PicAdapter adapter2=new Range_PicAdapter(getActivity(),R.layout.range_pic,range_pics);
        range_piclist=root.findViewById(R.id.rang_piclist);
        range_piclist.setAdapter(adapter2);
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        mTvSearch = root.findViewById(R.id.tv_search);

        final List<LabelModel> labelModelArrayList = new ArrayList<>();
        LabelModel tag1 = new LabelModel();
        tag1.setTextValue("时间");
        labelModelArrayList.add(tag1);

        LabelModel tag2= new LabelModel();
        tag2.setTextValue("学习");
        labelModelArrayList.add(tag2);

        LabelModel tag3= new LabelModel();
        tag3.setTextValue("闲聊");
        labelModelArrayList.add(tag3);

        LabelModel tag4= new LabelModel();
        tag4.setTextValue("运动");
        labelModelArrayList.add(tag4);

        LabelModel tag5= new LabelModel();
        tag5.setTextValue("游戏");
        labelModelArrayList.add(tag5);

        LabelModel tag6= new LabelModel();
        tag6.setTextValue("唱歌");
        labelModelArrayList.add(tag6);

        LabelModel tag7= new LabelModel();
        tag7.setTextValue("诗歌");
        labelModelArrayList.add(tag7);

        LabelModel tag8= new LabelModel();
        tag8.setTextValue("生活");
        labelModelArrayList.add(tag8);


        initRange();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        RangeAdapter adapter = new RangeAdapter(rangeList);
        recyclerView.setAdapter(adapter);


        refresh = root.findViewById(R.id.refresh);
        initSwipeRefresh();
        AutoRefresh();
        listener.onRefresh();
        listView = root.findViewById(R.id.list_view);
        initNews();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        get();
                        refresh.setRefreshing(false);
                    }
                }, 20);
            }
        });
        adapter1 = new PostAdapter(getActivity(), R.layout.post, newsList);

        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            post news = newsList.get(position);
            Intent intent = new Intent(getActivity(), NewsActivity.class);
            String news_headline = news.getHeadline();
            String news_writer = news.getWriter();
            String news_context = news.getContext();
            intent.putExtra("extra_headline", news_headline);
            intent.putExtra("extra_writer", news_writer);
            intent.putExtra("extra_context", news_context);
            startActivity(intent);
        });
        return root;
    }

    private void initRange_pic() {
        range_pic time=new range_pic(R.drawable.timg0);
        range_pics.add(time);
        range_pic study=new range_pic(R.drawable.timg1);
        range_pics.add(study);
        range_pic sports=new range_pic(R.drawable.timg2);
        range_pics.add(sports);
        range_pic games=new range_pic(R.drawable.timg3);
        range_pics.add(games);
        range_pic songs=new range_pic(R.drawable.timg4);
        range_pics.add(songs);
        range_pic poems=new range_pic(R.drawable.timg5);
        range_pics.add(poems);
        range_pic life=new range_pic(R.drawable.timg6);
        range_pics.add(life);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    initSwipeRefresh();
                    AutoRefresh();
                    listener.onRefresh();
                }
                break;
            default:
                break;
        }
    }

    public void initNews() {
        new PostTools().CommunityMessageShow(newsList);
    }

    public void AutoRefresh() {
        refresh.post(new Runnable() {
            @Override
            public void run () {
                refresh.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        get();
                        refresh.setRefreshing(false);
                    }
                },500);
            }

        });
    }

    public void get(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<CommunityItem> query = new BmobQuery<CommunityItem>();
                query.order("-updatedAt");
                query.findObjects(new FindListener<CommunityItem>() {
                    @Override
                    public void done(List<CommunityItem> list, BmobException e) {
                        List<CommunityItem> lists = new ArrayList<>();
                        if (list != null) {
                            System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                            final String[] title = new String[list.size()];
                            final String[] content = new String[list.size()];
                            final String[] author = new String[list.size()];

                            for (int i = 0; i < list.size(); i++) {
                                title[i] = list.get(i).getTitle();
                                content[i] = list.get(i).getContent();
                                author[i]=list.get(i).getAuthor().getName();
                            }
                            listView.setAdapter(new MyAdapter(getActivity(), title, content, author));
                        }
                    }
                });
            }
        }); //声明一个子线程
        thread.start();
    }

    private void initSwipeRefresh() {
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        get();
                        refresh.setRefreshing(false);
                        //添加数据设置适配器
                    }
                }, 20);
            }
        };
        refresh.setOnRefreshListener(listener);
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

    public void refresh(PostAdapter adapter)
    {
        adapter.notifyDataSetChanged();
    }
}