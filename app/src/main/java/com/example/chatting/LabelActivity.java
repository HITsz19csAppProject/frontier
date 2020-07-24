package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LabelActivity extends AppCompatActivity implements LabelLayoutView.OnInputValueListener {
    private String TAG = MainActivity.class.getSimpleName();
    private EditText editText;
    String context;
    Button sure;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        mIvBack = (ImageView)findViewById(R.id.im_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editText=findViewById(R.id.editText);
        sure=findViewById(R.id.sure);
        final LabelLayoutView labelLayoutView = findViewById(R.id.lablayout);
        final List<LabelModel> labelModelArrayList = new ArrayList<>();
        LabelModel tag1 = new LabelModel();
        tag1.setTextValue("#19级本科生");
        labelModelArrayList.add(tag1);

        LabelModel tag2= new LabelModel();
        tag2.setTextValue("#18级本科生");
        labelModelArrayList.add(tag2);

        LabelModel tag3= new LabelModel();
        tag3.setTextValue("#17级本科生");
        labelModelArrayList.add(tag3);

        LabelModel tag4= new LabelModel();
        tag4.setTextValue("#16级本科生");
        labelModelArrayList.add(tag4);

        LabelModel tag5= new LabelModel();
        tag5.setTextValue("#计算机学院");
        labelModelArrayList.add(tag5);

        LabelModel tag6= new LabelModel();
        tag6.setTextValue("#机电学院");
        labelModelArrayList.add(tag6);

        LabelModel tag7= new LabelModel();
        tag7.setTextValue("#建筑学院");
        labelModelArrayList.add(tag7);

        LabelModel tag8= new LabelModel();
        tag8.setTextValue("#理学院");
        labelModelArrayList.add(tag8);

        LabelModel tag9= new LabelModel();
        tag9.setTextValue("#文学院");
        labelModelArrayList.add(tag9);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context=editText.getText().toString();
                LabelModel model = new LabelModel();
                model.setTextValue("#"+context);
                labelModelArrayList.add(model);
                labelLayoutView.setStringList(labelModelArrayList);
                labelLayoutView.setOnInputValueListener(LabelActivity.this);
            }
        });
        labelLayoutView.setStringList(labelModelArrayList);
        labelLayoutView.setOnInputValueListener(LabelActivity.this);
    }


    @Override
    public void onInoutItem(String value) {
        Log.i(TAG, value);
    }
}