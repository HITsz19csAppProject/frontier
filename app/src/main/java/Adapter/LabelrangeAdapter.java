package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chatting.LabelLayoutView;
import com.example.chatting.R;
import AdaptObject.Labelrange;

import java.util.List;

import AdaptObject.LabelModel;

public class LabelrangeAdapter extends ArrayAdapter<Labelrange> {
    private final int resourceId;

    public LabelrangeAdapter(Context context, int textViewResourceId, List<Labelrange> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Labelrange labelrange = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView rangename = (TextView) view.findViewById(R.id.range_name);
        LabelLayoutView labelLayoutView=(LabelLayoutView)view.findViewById(R.id.lablayout);
        rangename.setText(labelrange.getRangename());
        labelLayoutView.setStringList((List<LabelModel>) labelrange.getLabelLayoutlist());
        return view;
    }
}

