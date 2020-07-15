package com.example.chatting;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<news> {
    private int resourceId;

    public NewsAdapter(Context context, int textViewResourceId, List<news> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        news news = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView news_headline=(TextView)view.findViewById(R.id.news_headline);
        TextView news_writer=(TextView)view.findViewById(R.id.news_writer);
        TextView news_context=(TextView)view.findViewById(R.id.news_context);
        news_headline.setText(news.getHeadline());
        news_writer.setText(news.getWriter());
        news_context.setText(news.getContext());
        return view;
    }
}
