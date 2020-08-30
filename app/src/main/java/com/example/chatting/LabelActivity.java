package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import AdaptObject.LabelModel;
import AdaptObject.Labelrange;
import Adapter.LabelrangeAdapter;

public class LabelActivity extends AppCompatActivity implements LabelLayoutView.OnInputValueListener {
    private String TAG = MainActivity.class.getSimpleName();
    private EditText editText;
    String context;
    Button sure;
    private ImageView mIvBack;
    Button ok;
    private List<Labelrange> LabelrangeList=new ArrayList<>();
    final List<LabelModel> labelModelArrayList = new ArrayList<>();
    final List<LabelModel> zhuanyeList=new ArrayList<>();
    final List<LabelModel> nianjiList=new ArrayList<>();
    final List<LabelModel> benyanList=new ArrayList<>();
    final List<LabelModel> banjiList=new ArrayList<>();
    final List<LabelModel> labelSearchArrayList = new ArrayList<>();

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
        ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        editText=findViewById(R.id.editText);
        sure=findViewById(R.id.sure);
        initLabelrange();
        LabelrangeAdapter adapter=new LabelrangeAdapter(LabelActivity.this,R.layout.activity_labelrange_acitivity,LabelrangeList);
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        LabelModel tag1 = new LabelModel();
        tag1.setTextValue("#材料科学与工程学院");
        labelModelArrayList.add(tag1);
        LabelModel tag2= new LabelModel();
        tag2.setTextValue("#机电工程与自动化学院");
        labelModelArrayList.add(tag2);
        LabelModel tag3= new LabelModel();
        tag3.setTextValue("#计算机科学与技术学院");
        labelModelArrayList.add(tag3);
        LabelModel tag4= new LabelModel();
        tag4.setTextValue("#建筑学院");
        labelModelArrayList.add(tag4);
        LabelModel tag5= new LabelModel();
        tag5.setTextValue("#理学院");
        labelModelArrayList.add(tag5);
        LabelModel tag6= new LabelModel();
        tag6.setTextValue("#经济管理学院");
        labelModelArrayList.add(tag6);
        LabelModel tag7= new LabelModel();
        tag7.setTextValue("#电子与信息工程学院");
        labelModelArrayList.add(tag7);
        LabelModel tag8= new LabelModel();
        tag8.setTextValue("#人文与社会科学学院");
        labelModelArrayList.add(tag8);
        LabelModel tag9= new LabelModel();
        tag9.setTextValue("#土木与环境工程学院");
        labelModelArrayList.add(tag9);

        LabelModel zhuanye1 = new LabelModel();
        zhuanye1.setTextValue("#计算机类");
        zhuanyeList.add(zhuanye1);
        LabelModel zhuanye2 = new LabelModel();
        zhuanye2.setTextValue("#电子信息类");
        zhuanyeList.add(zhuanye2);
        LabelModel zhuanye3 = new LabelModel();
        zhuanye3.setTextValue("#机械类");
        zhuanyeList.add(zhuanye3);
        LabelModel zhuanye4 = new LabelModel();
        zhuanye4.setTextValue("#自动化");
        zhuanyeList.add(zhuanye4);
        LabelModel zhuanye5= new LabelModel();
        zhuanye5.setTextValue("#电气类");
        zhuanyeList.add(zhuanye5);
        LabelModel zhuanye6 = new LabelModel();
        zhuanye6.setTextValue("#土木类");
        zhuanyeList.add(zhuanye6);
        LabelModel zhuanye7 = new LabelModel();
        zhuanye7.setTextValue("#环境科学与工程类");
        zhuanyeList.add(zhuanye7);
        LabelModel zhuanye8 = new LabelModel();
        zhuanye8.setTextValue("#材料类");
        zhuanyeList.add(zhuanye8);
        LabelModel zhuanye9 = new LabelModel();
        zhuanye9.setTextValue("#经济类");
        zhuanyeList.add(zhuanye9);
        LabelModel zhuanye10 = new LabelModel();
        zhuanye10.setTextValue("#会计类");
        zhuanyeList.add(zhuanye10);
        LabelModel zhuanye11 = new LabelModel();
        zhuanye11.setTextValue("#建筑类");
        zhuanyeList.add(zhuanye11);
        LabelModel zhuanye12 = new LabelModel();
        zhuanye12.setTextValue("#数学类");
        zhuanyeList.add(zhuanye12);


        LabelModel nianji1 = new LabelModel();
        nianji1.setTextValue("#20级");
        nianjiList.add(nianji1);
        LabelModel nianji2 = new LabelModel();
        nianji2.setTextValue("#19级");
        nianjiList.add(nianji2);
        LabelModel nianji3 = new LabelModel();
        nianji3.setTextValue("#18级");
        nianjiList.add(nianji3);
        LabelModel nianji4 = new LabelModel();
        nianji4.setTextValue("#17级");
        nianjiList.add(nianji4);

        LabelModel benyan1 = new LabelModel();
        benyan1.setTextValue("#本科生");
        benyanList.add(benyan1);
        LabelModel benyan2 = new LabelModel();
        benyan2.setTextValue("#研究生");
        benyanList.add(benyan2);

        LabelModel banji1 = new LabelModel();
        banji1.setTextValue("#一班");
        banjiList.add(banji1);
        LabelModel banji2 = new LabelModel();
        banji2.setTextValue("#二班");
        banjiList.add(banji2);
        LabelModel banji3 = new LabelModel();
        banji3.setTextValue("#三班");
        banjiList.add(banji3);
        LabelModel banji4 = new LabelModel();
        banji4.setTextValue("#四班");
        banjiList.add(banji4);
        LabelModel banji5 = new LabelModel();
        banji5.setTextValue("#五班");
        banjiList.add(banji5);
        LabelModel banji6 = new LabelModel();
        banji6.setTextValue("#六班");
        banjiList.add(banji6);
        LabelModel banji7 = new LabelModel();
        banji7.setTextValue("#七班");
        banjiList.add(banji7);
        LabelModel banji8 = new LabelModel();
        banji8.setTextValue("#八班");
        banjiList.add(banji8);
        LabelModel banji9 = new LabelModel();
        banji9.setTextValue("#九班");
        banjiList.add(banji9);
        LabelModel banji10 = new LabelModel();
        banji10.setTextValue("#十班");
        banjiList.add(banji10);





        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context=editText.getText().toString();
                LabelModel model = new LabelModel();
                model.setTextValue("#"+context);
                labelSearchArrayList.clear();
                labelSearchArrayList.add(model);
                LabelrangeList.clear();
                Labelrange xinjian=new Labelrange("新建",labelSearchArrayList);
                LabelrangeList.add(xinjian);
                //labelLayoutView.setStringList(labelSearchArrayList);
                //labelLayoutView.setOnInputValueListener(LabelActivity.this);
            }
        });
    }

    private void initLabelrange() {
        Labelrange xueyuan=new Labelrange("学院",labelModelArrayList);
        LabelrangeList.add(xueyuan);
        Labelrange zhuanye=new Labelrange("专业",zhuanyeList);
        LabelrangeList.add(zhuanye);
        Labelrange nianji=new Labelrange("年级",nianjiList);
        LabelrangeList.add(nianji);
        Labelrange benyan=new Labelrange("本研",benyanList);
        LabelrangeList.add(benyan);
        Labelrange banji=new Labelrange("班级",banjiList);
        LabelrangeList.add(banji);



    }


    @Override
    public void onInoutItem(String value) {
        Log.i(TAG, value);
    }
}