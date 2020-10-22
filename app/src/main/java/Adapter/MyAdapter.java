/**
 * 信息发布、信息交互界面呈现信息的Adapter.
 * 使用方法可见ReceiveActivity类中的getMessage函数。
 */
package Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chatting.R;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private String[] title;
    private String[] content;
    private String[] author;
    private class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_author;
    }

    public MyAdapter(Context context, String[] title, String[] content, String[] author) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.author = author;
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
            convertView = inflater.inflate(R.layout.news, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.news_headline);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.news_context);
            viewHolder.tv_author=(TextView)convertView.findViewById(R.id.news_writer);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(title[position]);
        viewHolder.tv_content.setText(content[position]);
        viewHolder.tv_author.setText(author[position]);
        return convertView;
    }
}
