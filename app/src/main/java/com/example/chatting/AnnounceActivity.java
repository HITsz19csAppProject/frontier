package com.example.chatting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelector;

import java.util.ArrayList;
import java.util.List;

import AdaptObject.news;
import Adapter.ImageAdapter;
import Adapter.MyAdapter;
import Adapter.NewsAdapter;
import Bean.CommunityItem;
import Bean.MessageItem;
import Bean.User;
import Tools.GraphTools;
import Tools.ServerTools;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 信息交互界面的消息发布（上传）
 */
public class AnnounceActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    private static final int PERMISSION_READ_EXTERNAL_REQUEST_CODE = 0x00000013;

    private Button publish, addLabel;
    private Button mAdd_photo;
    private EditText my_headline;
    private EditText my_context;
    private ImageView mIvBack;
    private ImageAdapter mAdapter;
    private ListView listView;

    private ArrayList<String> myImages;
    private String myHeadline;
    private String myContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("进入announceActivity");
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        InitImage();
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        mIvBack = (ImageView)findViewById(R.id.im_back);
        publish=findViewById(R.id.publish);
        addLabel = findViewById(R.id.btn_addlabel);
        my_headline=findViewById(R.id.my_headline);
        my_context=findViewById(R.id.my_context);
        listView = (ListView) findViewById(R.id.list_view);
        mAdd_photo = findViewById(R.id.add_photo);
        mAdapter = new ImageAdapter(this);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });  //返回

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("已经点击");
                Intent intent1 = new Intent();
                myHeadline = my_headline.getText().toString();
                myContext = my_context.getText().toString();
                if(!myHeadline.isEmpty()&&!myContext.isEmpty())
                {
                    System.out.println("获取成功");
                    CommunityItem newMessage = new CommunityItem();
                    newMessage.setTitle(myHeadline);
                    newMessage.setContent(myContext);
                    newMessage.setImages(myImages);

                    new ServerTools().BeforeSaveCommunityMessage(AnnounceActivity.this, newMessage);

                    intent1.putExtra("headline_return", myHeadline);
                    intent1.putExtra("context_return", myContext);
                    setResult(RESULT_OK, intent1);
                    finish();
                }
                else{
                    System.out.println("获取失败");
                    my_headline.setText("error");
                }
            }
        });
        //标签的添加
        addLabel.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnounceActivity.this,LabelActivity.class);
                startActivity(intent);
            }
        });

        mAdd_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(AnnounceActivity.this, REQUEST_CODE); // 打开相册
            }
        });

    }

    public void get(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                BmobQuery<CommunityItem> query = new BmobQuery<CommunityItem>();
                query.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
                query.order("-updatedAt");
                //包含作者信息
                query.include("author");
                query.findObjects(new FindListener<CommunityItem>() {
                    @Override
                    public void done(List<CommunityItem> list, BmobException e) {
                        List<CommunityItem> lists = new ArrayList<>();
                        if (list != null) {
                            System.out.println("查询成功" + list.get(0).getTitle() + list.get(0).getContent());
                            final String[] title = new String[list.size()];
                            final String[] content = new String[list.size()];
                            final String[] author = new String[list.size()];

                            for (int i = 0; i < list.size(); i++) {
                                title[i] = list.get(i).getTitle();
                                content[i] = list.get(i).getContent();
                                author[i]=list.get(i).getAuthor().getName();
                            }
                            listView.setAdapter(new MyAdapter(getApplication(), title, content,author));
                        }
                    }
                });
            }
        }); //声明一个子线程
        thread.start();
    }

    private void InitImage() {
        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //预加载手机图片。加载图片前，请确保app有读取储存卡权限
            ImageSelector.preload(this);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }

        int hasReadExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //预加载手机图片。加载图片前，请确保app有读取储存卡权限
            ImageSelector.preload(this);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL_REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
//            Log.d("ImageSelector", "是否是拍照图片：" + isCameraImage);
            myImages = images;
            mAdapter.refresh(images);
        }
    }

    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //预加载手机图片
                ImageSelector.preload(this);
            } else {
                //拒绝权限。
            }
        }
    }
}
