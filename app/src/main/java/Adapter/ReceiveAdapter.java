package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chatting.MessageDataBaseUtils;
import com.example.chatting.R;

public class ReceiveAdapter extends BaseAdapter {
    private Context context;
    private String[] title;
    private String[] content;
    private String[] author;
    private String[] isRead;
    private MessageDataBaseUtils utils ; //数据库操作
    private class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_author;
        TextView tv_isRead;
    }

    public ReceiveAdapter(Context context, String[] title, String[] content,String[] author,String[] isRead) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isRead = isRead;
    }

    public ReceiveAdapter(){

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
            convertView = inflater.inflate(R.layout.received_news, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.news_headline);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.news_context);
            viewHolder.tv_author=(TextView)convertView.findViewById(R.id.news_writer);
            viewHolder.tv_isRead = (TextView)convertView.findViewById(R.id.tv_isread);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (isRead[position]==null || isRead[position].equals("0") ){
            viewHolder.tv_isRead.setText("未读");
            viewHolder.tv_isRead.setTextColor(Color.parseColor("#00aa00"));
        }
        else if(isRead[position].equals("1")){
            viewHolder.tv_isRead.setText("已读");
            viewHolder.tv_isRead.setTextColor(Color.parseColor("#888888"));
        }

        viewHolder.tv_title.setText(title[position]);
        viewHolder.tv_content.setText(content[position]);
        viewHolder.tv_author.setText(author[position]);
        return convertView;
    }
}
