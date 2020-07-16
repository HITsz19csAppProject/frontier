package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForthActivity extends AppCompatActivity {
    EditText tv_search;
    Button tv_schedule;
    Button tv_related;
    Button tv_explore;
    Button btn_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);
        tv_search=findViewById(R.id.tv_search);
        tv_schedule=findViewById(R.id.tv_schedule);
        tv_related=findViewById(R.id.tv_related);
        tv_explore=findViewById(R.id.tv_explore);
        btn_post=findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForthActivity.this,PostActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        tv_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForthActivity.this,ExploreActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }



}
