package com.example.chatting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatting.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import Bean.MessageItem;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */



    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_publish, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
//        }
        return view;
    }
    @Override

    public void onActivityCreated( Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


    }

    public void get(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                BmobQuery<MessageItem> query = new BmobQuery<MessageItem>();
                query.findObjects(new FindListener<MessageItem>(){

                    @Override
                    public void done(List<MessageItem> list, BmobException e) {
                        List<MessageItem> lists = new ArrayList<>();
                        if (list != null) {
                            //System.out.println("查询成功"+list.get(0).getTitle()+list.get(0).getContent());
                            final String[] title  =  new String[list.size()];
                            final String[] content  =  new String[list.size()];

                            for(int i = 0;i<list.size();i++){
                                title[i] = list.get(i).getTitle();
                                content[i] = list.get(i).getContent();
                            }
                            class MyAdapter extends BaseAdapter {
                                private Context context ;
                                public MyAdapter(Context context){
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
                                    if (convertView == null){
                                        LayoutInflater inflater = LayoutInflater.from(context);
                                        convertView = inflater.inflate(R.layout.activity_announce, null);//实例化一个布局文件
                                        viewHolder = new ViewHolder();
                                        viewHolder.tv_title = (TextView)convertView.findViewById(R.id.my_headline);
                                        viewHolder.tv_content = (TextView)convertView.findViewById(R.id.my_context);
                                        convertView.setTag(viewHolder);

                                    }else {
                                        viewHolder = (ViewHolder) convertView.getTag();
                                    }

                                    viewHolder.tv_title.setText(title[position]);
                                    viewHolder.tv_content.setText(content[position]);
                                    return convertView;
                                }

                                class ViewHolder{
                                    TextView tv_title;
                                    TextView tv_content;
                                }
                            }
                            listView.setAdapter(new MyAdapter(getActivity()));
                        }
                    }
                });
            }
        }); //声明一个子线程
        thread.start();
    }

}