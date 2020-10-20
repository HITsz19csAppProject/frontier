package com.example.chatting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private EditText mEtSearch;
    private Button mBtnSchedule;
    private Button mBtnRelated;
    private Button mBtnExplore;
    private Button btn_post;
    private Button btn_related;
    private Button my_receieve;
    private Button my_publish;
    ViewPager vp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotificationViewModel.class);
        View root = inflater.inflate(R.layout.notification_fragment, container, false);
        mEtSearch = root.findViewById(R.id.et_search);
        mBtnSchedule = root.findViewById(R.id.btn_schedule);
        mBtnExplore = root.findViewById(R.id.btn_explore);
        btn_post = root.findViewById(R.id.btn_post);
        my_receieve=root.findViewById(R.id.my_receive);
        my_publish=root.findViewById(R.id.my_publish);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        mBtnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExploreActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        mBtnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);
            }
        });
        my_receieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReceiveActivity.class);
                startActivity(intent);
            }
        });
        my_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);
            }
        });
        

        DrawableUtil drawableUtil = new DrawableUtil(mEtSearch, new DrawableUtil.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {

            }

            @Override
            public void onRight(View v, Drawable right) {
                if (mEtSearch.length() > 1){
                    mEtSearch.setText("");
                }
            }
        });

        return root;
    }

}