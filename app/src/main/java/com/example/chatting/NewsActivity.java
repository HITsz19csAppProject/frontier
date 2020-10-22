package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class NewsActivity extends AppCompatActivity {
    private TextView News_headline;
    private TextView News_writer;
    private TextView News_context;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.collect:
                Toast.makeText(this, "已加入收藏夹", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                Toast.makeText(this, "已加入日程", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this, "让更多的人了解吧", Toast.LENGTH_SHORT).show();
                break;
            case R.id.warn:
                Toast.makeText(this, "举报不良评论", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        News_headline = findViewById(R.id.News_headline);
        News_writer = findViewById(R.id.News_writer);
        News_context = findViewById(R.id.News_context);

        Intent intent = getIntent();

        News_headline.setText(intent.getStringExtra("extra_headline"));
        News_writer.setText(intent.getStringExtra("extra_writer"));
        News_context.setText(intent.getStringExtra("extra_context"));
    }
}
