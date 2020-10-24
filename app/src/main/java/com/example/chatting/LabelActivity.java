package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import AdaptObject.LabelModel;
import AdaptObject.Labelrange;
import Adapter.LabelrangeAdapter;
import Bean.User;
import Tools.AddLabels;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LabelActivity extends AppCompatActivity implements LabelLayoutView.OnInputValueListener {
    private String TAG = MainActivity.class.getSimpleName();
    private EditText editText;
    String context;
    Button sure;
    Button ok;
    private List<Labelrange> LabelrangeList=new ArrayList<>();
    final List<LabelModel> labelModelArrayList = new ArrayList<>();
    final List<LabelModel> zhuanyeList=new ArrayList<>();
    final List<LabelModel> nianjiList=new ArrayList<>();
    final List<LabelModel> benyanList=new ArrayList<>();
    final List<LabelModel> banjiList=new ArrayList<>();
    final List<LabelModel> labelSearchArrayList = new ArrayList<>();
    AddLabels addLabels = new AddLabels();//加入标签的对象
    List<User> Ousers = new ArrayList<>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {
                Ousers =(List<User>) msg.getData().getSerializable("data");
                for(User u:Ousers)
                {
                    System.out.println(u.getPhoneNum()+"a");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
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
        zhuanye1.setTextValue("#计算机");
        zhuanyeList.add(zhuanye1);
        LabelModel zhuanye2 = new LabelModel();
        zhuanye2.setTextValue("#电子信息");
        zhuanyeList.add(zhuanye2);
        LabelModel zhuanye3 = new LabelModel();
        zhuanye3.setTextValue("#机械");
        zhuanyeList.add(zhuanye3);
        LabelModel zhuanye4 = new LabelModel();
        zhuanye4.setTextValue("#自动化");
        zhuanyeList.add(zhuanye4);
        LabelModel zhuanye5= new LabelModel();
        zhuanye5.setTextValue("#电气");
        zhuanyeList.add(zhuanye5);
        LabelModel zhuanye6 = new LabelModel();
        zhuanye6.setTextValue("#土木");
        zhuanyeList.add(zhuanye6);
        LabelModel zhuanye7 = new LabelModel();
        zhuanye7.setTextValue("#环境科学与工程");
        zhuanyeList.add(zhuanye7);
        LabelModel zhuanye8 = new LabelModel();
        zhuanye8.setTextValue("#材料");
        zhuanyeList.add(zhuanye8);
        LabelModel zhuanye9 = new LabelModel();
        zhuanye9.setTextValue("#经济");
        zhuanyeList.add(zhuanye9);
        LabelModel zhuanye10 = new LabelModel();
        zhuanye10.setTextValue("#会计");
        zhuanyeList.add(zhuanye10);
        LabelModel zhuanye11 = new LabelModel();
        zhuanye11.setTextValue("#建筑");
        zhuanyeList.add(zhuanye11);
        LabelModel zhuanye12 = new LabelModel();
        zhuanye12.setTextValue("#数学");
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
        banji1.setTextValue("#1班");
        banjiList.add(banji1);
        LabelModel banji2 = new LabelModel();
        banji2.setTextValue("#2班");
        banjiList.add(banji2);
        LabelModel banji3 = new LabelModel();
        banji3.setTextValue("#3班");
        banjiList.add(banji3);
        LabelModel banji4 = new LabelModel();
        banji4.setTextValue("#4班");
        banjiList.add(banji4);
        LabelModel banji5 = new LabelModel();
        banji5.setTextValue("#5班");
        banjiList.add(banji5);
        LabelModel banji6 = new LabelModel();
        banji6.setTextValue("#6班");
        banjiList.add(banji6);
        LabelModel banji7 = new LabelModel();
        banji7.setTextValue("#7班");
        banjiList.add(banji7);
        LabelModel banji8 = new LabelModel();
        banji8.setTextValue("#8班");
        banjiList.add(banji8);
        LabelModel banji9 = new LabelModel();
        banji9.setTextValue("#9班");
        banjiList.add(banji9);
        LabelModel banji10 = new LabelModel();
        banji10.setTextValue("#10班");
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
                traverseLabels();
                labelBmobquery();

               // Ousers = getAddLabels();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("list_1",(Serializable)Ousers);
                intent.putExtras(bundle);
                setResult(0,intent);
                LabelActivity.this.finish();
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

    //对所有的标签进行检索如果已经被点击，则将其加入ArrayList
    public void traverseLabels(){
        for(LabelModel lamo:labelModelArrayList){
            if(lamo.isClick())addLabels.academy.add(lamo.getTextValue().substring(1));
        }

        for(LabelModel lamo:zhuanyeList){
            if(lamo.isClick())addLabels.speciality.add(lamo.getTextValue().substring(1));
        }

        for(LabelModel lamo:nianjiList){
            if(lamo.isClick())addLabels.grade.add("20"+lamo.getTextValue().substring(1,3));
        }

        for(LabelModel lamo:banjiList){
            if(lamo.isClick())addLabels.Class.add(lamo.getTextValue().substring(1));
        }
        if(addLabels.speciality.isEmpty())addLabels.speciality = (ArrayList)labelModelArrayList;
        if(addLabels.academy.isEmpty())addLabels.academy = (ArrayList)labelModelArrayList;
        if(addLabels.grade.isEmpty())addLabels.grade = (ArrayList)labelModelArrayList;
        if(addLabels.Class.isEmpty())addLabels.Class = (ArrayList)labelModelArrayList;
    }

    private void labelBmobquery(){
        BmobQuery<User> userGrade = new BmobQuery<User>();
        userGrade.addWhereContainedIn("Grade",addLabels.grade);

        BmobQuery<User> userClass = new BmobQuery<User>();
        userGrade.addWhereContainedIn("ClassID",addLabels.Class);

        BmobQuery<User> useracademy = new BmobQuery<User>();
        useracademy.addWhereContainedIn("",addLabels.academy);

        BmobQuery<User> userSpeciality = new BmobQuery<User>();
        userGrade.addWhereContainedIn("Major",addLabels.speciality);

        List<BmobQuery<User>> andQuerys = new ArrayList<BmobQuery<User>>();
        andQuerys.add(userGrade);
        andQuerys.add(userClass);
        //andQuerys.add(useracademy);
        andQuerys.add(userSpeciality);


        BmobQuery<User> query = new BmobQuery<User>();
        query.and(andQuerys);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    /**
                     在此处对已选中的人进行下一步操作
                     */
                    for(User u:list)
                    {
                        System.out.println(u.getPhoneNum());
                    }
                    Bundle bundle = new Bundle();
                    Message message = new Message();
                    bundle.putSerializable("data",(Serializable)list);
                    message.what = 111;
                    message.setData(bundle);
                    handler.sendMessage(message);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());//发生错误，返回错误代码
                }
            }
        });
    }

    @Override
    public void onInoutItem(String value) {
        Log.i(TAG, value);
    }
}