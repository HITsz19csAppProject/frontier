package com.example.chatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import java.util.List;

public class LinearApater extends RecyclerView.Adapter<LinearApater.ViewHolder> {
=======
import com.example.chatting.R;

import java.util.List;

class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.ViewHolder> {
>>>>>>> 57a7597d622aeacff8e3aff3b8bf31bc4cc297a6

    private Context mContext;
    private List<String> titleList;
    private List<Boolean> booleanList;
    private ViewHolder viewHolder;
    private List<String> authorList;
    private List<String> bodyList;
    private OnRecyclerViewItemClickListener myClickItemListener;// 声明自定义的接口

<<<<<<< HEAD
    public LinearApater(Context mContext, List<String> titleList, List<Boolean> booleanList, List<String> authorList, List<String> bodyList) {
=======
    public LinearAdapter(Context mContext, List<String> titleList, List<Boolean> booleanList,List<String> authorList,List<String> bodyList) {
>>>>>>> 57a7597d622aeacff8e3aff3b8bf31bc4cc297a6
        this.mContext = mContext;
        this.titleList = titleList;
        this.booleanList = booleanList;
        this.authorList = authorList;
        this.bodyList = bodyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_linear_item, parent, false);
        viewHolder = new ViewHolder(view, myClickItemListener);
        return viewHolder;
    }

    /**
     * 定义public方法用以将接口暴露给外部
     */
    public  void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.myClickItemListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (!booleanList.get(position)) {
            holder.itemView.setBackgroundResource(R.color.colorWhite);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorLightGrey);
        }
        holder.tvTitle.setText(titleList.get(position));
        holder.tvAuthor.setText(authorList.get(position));
        holder.tvBody.setText(bodyList.get(position));
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    /**
     * 自定义接口
     */
    public interface OnRecyclerViewItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle,tvAuthor,tvBody;
        private OnRecyclerViewItemClickListener mListener;// 声明自定义的接口

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvBody = itemView.findViewById(R.id.tv_body);
        }

        @Override
        public void onClick(View v) {
            //getAdapterPosition()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mListener.onItemClick(v, getAdapterPosition());
        }
    }

}
