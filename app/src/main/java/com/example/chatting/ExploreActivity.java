package com.example.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import Adapter.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import Adapter.LinearApater;

public class ExploreActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mTvSearch;
    private RecyclerView mRvMain;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Boolean> booleanList = new ArrayList<>();
    private ArrayList<String> authorList = new ArrayList<>();
    private ArrayList<String> bodyList = new ArrayList<>();
    private LinearApater linearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        mIvBack = (ImageView)findViewById(R.id.im_back);
        mTvSearch = (TextView)findViewById(R.id.tv_search);
        setOnclickListeners();
    }

    public void setOnclickListeners(){
        onClick onClick = new onClick();
        mTvSearch.setOnClickListener(onClick);
        mIvBack.setOnClickListener(onClick);
    }

    private class onClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.im_back:
                    onBackPressed();
                    break;

            }
        }
    }
}