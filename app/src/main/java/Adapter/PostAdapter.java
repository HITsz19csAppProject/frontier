package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chatting.R;
import AdaptObject.post;

import java.util.List;
import java.util.concurrent.FutureTask;

public class PostAdapter extends ArrayAdapter<post> {
    private int resourceId;
    private FutureTask mNewList;

    public PostAdapter(Context context, int textViewResourceId, List<post> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        post news = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView news_headline = (TextView) view.findViewById(R.id.news_headline);
        TextView news_writer = (TextView) view.findViewById(R.id.news_writer);
        TextView news_context = (TextView) view.findViewById(R.id.news_context);
        news_headline.setText(news.getHeadline());
        news_writer.setText(news.getWriter());
        news_context.setText(news.getContext());
        return view;
    }

}

