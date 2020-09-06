package com.example.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.NewsAdapter;
import Adapter.RangeAdapter;
import AdaptObject.LabelModel;
import AdaptObject.news;
import AdaptObject.range;

import static android.app.Activity.RESULT_OK;

public class ChattingFragment extends Fragment {

    private ChattingViewModel chattingViewModel;
    private List<range> rangeList = new ArrayList<>();
    private List<news> newsList = new ArrayList<>();
    private Button add;
    private EditText mTvSearch;
    private NewsAdapter adapter1;
    private Button sum;
    public DrawerLayout drawerLayout;
    private Button sure;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chattingViewModel =
                ViewModelProviders.of(this).get(ChattingViewModel.class);
        View root = inflater.inflate(R.layout.chatting_fragment, container, false);
        add = root.findViewById(R.id.add);
        sum=root.findViewById(R.id.sum);
        sure=root.findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        drawerLayout=(DrawerLayout) root.findViewById(R.id.drawer_layout_home);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnnounceActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        mTvSearch = root.findViewById(R.id.tv_search);

        final LabelLayoutView labelLayoutView =root. findViewById(R.id.range_layout);
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

        labelLayoutView.setStringList(labelModelArrayList);

        initRange();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        RangeAdapter adapter = new RangeAdapter(rangeList);
        recyclerView.setAdapter(adapter);

        initNews();
        adapter1 = new NewsAdapter(getActivity(), R.layout.news, newsList);
        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                news news = newsList.get(position);
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                String news_headline = news.getHeadline();
                String news_writer = news.getWriter();
                String news_context = news.getContext();
                intent.putExtra("extra_headline", news_headline);
                intent.putExtra("extra_writer", news_writer);
                intent.putExtra("extra_context", news_context);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
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

}