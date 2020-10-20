package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chatting.R;

import java.util.List;

import AdaptObject.news;
import AdaptObject.range_pic;



public class Range_PicAdapter  extends ArrayAdapter<range_pic> {
    private int resourceId;

    public Range_PicAdapter( Context context, int textViewResourceId,  List<range_pic> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        range_pic range_pic = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView pic=(ImageView)view.findViewById(R.id.rang_pic);
        pic.setImageResource(range_pic.getImgId());
        return view;
    }
}
