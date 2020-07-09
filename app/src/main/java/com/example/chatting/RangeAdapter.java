package com.example.chatting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RangeAdapter extends RecyclerView.Adapter<RangeAdapter.ViewHolder> {
    private List<range> MrangeList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView range_name;

        public ViewHolder(View view) {
            super(view);
            range_name = (TextView) view.findViewById(R.id.range_name);
        }
    }


    public RangeAdapter(List<range> rangeList) {
        MrangeList = rangeList;
    }

    @Override
    public RangeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.range, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RangeAdapter.ViewHolder holder, int position) {
        range range = MrangeList.get(position);
        holder.range_name.setText(range.getName());
    }

    @Override
    public int getItemCount() {
        return MrangeList.size();
    }
}

