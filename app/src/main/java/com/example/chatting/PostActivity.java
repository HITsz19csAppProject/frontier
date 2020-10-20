package com.example.chatting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.donkingliang.imageselector.utils.ImageSelector;

import java.util.ArrayList;

import Adapter.ImageAdapter;
import Bean.MessageItem;
import Tools.ServerTools;

/**
 * 信息通知界面的消息发布（上传）
 */
public class PostActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    private static final int PERMISSION_READ_EXTERNAL_REQUEST_CODE = 0x00000013;

    private ImageView imageView;
    private Button mBtnAddLabel;
    private Button mPublish;
    private Button mAdd_photo;
    private EditText my_headline;
    private EditText my_context;
    private ImageAdapter mAdapter;
    private ImageView mIvBack;

    private String myHeadline;
    private String myContext;
    private ArrayList<String> myImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        InitImage();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        mIvBack = findViewById(R.id.im_back);
        imageView = findViewById(R.id.im_back);
        mPublish = findViewById(R.id.publish);
        my_headline = findViewById(R.id.my_headline);
        my_context = findViewById(R.id.my_context);
        mBtnAddLabel = findViewById(R.id.btn_addlabel);
        mAdd_photo = findViewById(R.id.add_photo);
        mAdapter = new ImageAdapter(this);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                myHeadline = my_headline.getText().toString();
                myContext = my_context.getText().toString();
                if(!myHeadline.isEmpty() && !myContext.isEmpty())
                {
                    MessageItem newMessage =new MessageItem();
                    newMessage.setContent(myContext);
                    newMessage.setTitle(myHeadline);
                    newMessage.setImages(myImages);

                    new ServerTools().BeforeSaveMessage(PostActivity.this, newMessage);

                    intent.putExtra("headline_return", myHeadline);
                    intent.putExtra("context_return", myContext);
                    //todo: images_return

                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    my_headline.setText("error");
                }
            }
        });

        mBtnAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, LabelActivity.class);
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
                        .start(PostActivity.this, REQUEST_CODE); // 打开相册
            }
        });
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