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
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import Bean.MessageItem;
import cn.bmob.v3.Bmob;

public class ReceiveAdapter extends BaseAdapter {
    private Context context;
    private List<MessageItem> list;
    private String[] isRead;
    private MessageDataBaseUtils utils ; //数据库操作
    private class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_author;
        TextView tv_isRead;
        NineGridView img_NineGrid;
    }

    public ReceiveAdapter(Context context, List<MessageItem> list,String[] isRead) {
        this.context = context;
        this.list = list;
        this.isRead = isRead;
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
            convertView = inflater.inflate(R.layout.received_news, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.news_headline);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.news_context);
            viewHolder.tv_author=(TextView)convertView.findViewById(R.id.news_writer);
            viewHolder.tv_isRead = (TextView)convertView.findViewById(R.id.tv_isread);
            viewHolder.img_NineGrid = convertView.findViewById(R.id.nineGrid);
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
