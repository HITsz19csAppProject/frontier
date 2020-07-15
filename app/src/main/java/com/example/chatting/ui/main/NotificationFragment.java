package com.example.chatting.ui.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.chatting.DrawableUtil;
import com.example.chatting.ExploreActivity;
import com.example.chatting.PostActivity;
import com.example.chatting.R;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private EditText tv_search;
    private Button tv_schedule;
    private Button tv_related;
    private Button tv_explore;
    private Button btn_post;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotificationViewModel.class);
        View root = inflater.inflate(R.layout.notification_fragment, container, false);
        tv_search = root.findViewById(R.id.tv_search);
        tv_schedule = root.findViewById(R.id.tv_schedule);
        tv_related = root.findViewById(R.id.tv_related);
        tv_explore = root.findViewById(R.id.tv_explore);
        btn_post = root.findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        tv_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExploreActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        DrawableUtil drawableUtil = new DrawableUtil(tv_search, new DrawableUtil.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {

            }

            @Override
            public void onRight(View v, Drawable right) {
                if (tv_search.length() > 1){
                    tv_search.setText("");
                }
            }
        });
        return root;
    }
}