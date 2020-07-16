package com.example.chatting;

<<<<<<< HEAD
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private long exitTime = 0;
    private Handler mHandler = new Handler();
    private Runnable mFinish = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };
=======
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity {

    private List<range> rangeList = new ArrayList<>();
    private List<news> newsList=new ArrayList<>();
    private Button add;
    private Button notice;
    private Button myself;
    private NewsAdapter adapter1;
    private Button btn_login;
    private TextView et_username;
    private TextView et_password;
>>>>>>> 57a7597d622aeacff8e3aff3b8bf31bc4cc297a6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_notification, R.id.navigation_chatting, R.id.navigation_center)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((System.currentTimeMillis()-exitTime) > 2000){
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            mHandler.postDelayed(mFinish, 0);
        }
        //return super.onKeyDown(keyCode, event);
        return false;
=======

        Bmob.initialize(this,"0c073307ca22bf3e1268f1f70bef2941");

        addControl();
        addLogin();

        add = findViewById(R.id.add);
        notice=findViewById(R.id.notice);
        myself=findViewById(R.id.myself);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ForthActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FifthActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        initRange();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        RangeAdapter adapter = new RangeAdapter(rangeList);
        recyclerView.setAdapter(adapter);

        initNews();
        adapter1 = new NewsAdapter(MainActivity.this, R.layout.news, newsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                news news = newsList.get(position);
                Intent intent = new Intent(MainActivity.this, secondActivity.class);
                String news_headline = news.getHeadline();
                String news_writer = news.getWriter();
                String news_context = news.getContext();
                intent.putExtra("extra_headline", news_headline);
                intent.putExtra("extra_writer", news_writer);
                intent.putExtra("extra_context", news_context);
                startActivity(intent);
            }
        });
    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK) {
                        String myheadline = data.getStringExtra("headline_return");
                        String mycontext = data.getStringExtra("context_return");
                        news M = new news(myheadline, "me", mycontext);
                        newsList.add(M);
                        refresh(adapter1);
                    }
                    break;
                default:
                    break;
            }
        }



    private void initNews() {
        for(int i=0;i<2;i++)
        {
            news N1=new news("Apple","John","I love apples");
            news N2=new news("Grape","JoJo","I love grapes");
            news N3=new news("Tomato","Lisa","I love tomatoes");
            news N4=new news("Cherry","Lina","I love cherry");
            news N5=new news("Strawberry","Jack","I love strawberries");
            news N6=new news("Pear","Angel","I love pear");

            newsList.add(N1);
            newsList.add(N2);
            newsList.add(N3);
            newsList.add(N4);
            newsList.add(N5);
            newsList.add(N6);
        }
    }

    private void initRange() {
        for(int i=0;i<1;i++)
        {
            range time = new range("时间");
            range study=new range("学习");
            range talk=new range("闲聊");
            range sports=new range("运动");
            range games=new range("游戏");
            range songs=new range("唱歌");
            range poem=new range("诗歌");
            range life=new range("生活");

            rangeList.add(time);
            rangeList.add(study);
            rangeList.add(talk);
            rangeList.add(sports);
            rangeList.add(games);
            rangeList.add(songs);
            rangeList.add(poem);
            rangeList.add(life);
        }
    }

    public void refresh(NewsAdapter adapter)
    {
        adapter.notifyDataSetChanged();
    }

    //bmob登录方法
    private void addLogin() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lgU = et_username.getText().toString().trim();
                String lgp = et_password.getText().toString().trim();
                final BmobUser bu2 = new BmobUser();
                bu2.setUsername(lgU);
                bu2.setPassword(lgp);
                bu2.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            //登陆成功就跳转到主页面
                            Intent in_success = new Intent(MainActivity.this, FifthActivity.class);
                            startActivity(in_success);

                        } else {
                            Toast.makeText(MainActivity.this, "账户名或密码不正确", Toast.LENGTH_SHORT).show();
                            //loge(e);
                        }

                    }
                });


            }


        });
    }

    private void addControl() {

        et_username = (TextView) findViewById(R.id.et_username);
        et_password = (TextView) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
>>>>>>> 57a7597d622aeacff8e3aff3b8bf31bc4cc297a6
    }

}
