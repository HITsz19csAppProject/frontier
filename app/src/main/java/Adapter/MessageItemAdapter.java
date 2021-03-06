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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatting.R;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import Bean.MessageItem;
import cn.bmob.v3.Bmob;

public class MessageItemAdapter extends BaseAdapter {
    private Context context;
    private List<MessageItem> list;

    private class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_author;
        NineGridView img_NineGrid;
    }

    public MessageItemAdapter(Context context, List<MessageItem> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<MessageItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position).getTitle();
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
            viewHolder.img_NineGrid = convertView.findViewById(R.id.nineGrid);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MessageItem item = list.get(position);
        viewHolder.tv_title.setText(item.getTitle());
        viewHolder.tv_content.setText(item.getContent());
        viewHolder.tv_author.setText(item.getAuthor().getName());

        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        ArrayList<String> ImageNames = item.getImageNames();
        if (ImageNames != null && ImageNames.size() > 0) {
            for (String ImageName : ImageNames) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(Bmob.getCacheDir().getPath() + "/" + ImageName);
                info.setBigImageUrl(Bmob.getCacheDir().getPath() + "/" + ImageName);
                imageInfo.add(info);
            }
        }
        viewHolder.img_NineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));

        return convertView;
    }
}
