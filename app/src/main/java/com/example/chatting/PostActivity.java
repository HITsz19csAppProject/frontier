package com.example.chatting;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import AdaptObject.LabelModel;
import Bean.MessageItem;
import Bean.User;
import Tools.GraphTools;
import Tools.ServerTools;
import cn.bingoogolapple.photopicker.activity.BGAPPToolbarActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 信息通知界面的消息发布（上传）
 */
public class PostActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {
    private static final int LABEL_ACTIVITY_REQUEST_CODE = 0;
    private static final int PRC_PHOTO_PICKER = 1;

    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;

    private ImageView imageView;
    private Button mBtnAddLabel;
    private Button mPublish;
    private EditText my_headline;
    private EditText my_context;
    private ImageView mIvBack;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private CustomView customView;

    private String myHeadline;
    private String myContext;
    private ArrayList<String> myImages;
    private List<LabelModel> labelModelList;
    private List<User> uList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
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
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        customView = findViewById(R.id.custom_view);

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
                myHeadline = my_headline.getText().toString();
                myContext = my_context.getText().toString();
                myImages = mPhotosSnpl.getData();
                if(!myHeadline.isEmpty() && !myContext.isEmpty()) {
                    MessageItem newMessage =new MessageItem();
                    newMessage.setContent(myContext);
                    newMessage.setTitle(myHeadline);
                    newMessage.setImages(myImages);
                    newMessage.setObjectiveUsers(uList);
                    new GraphTools<>(newMessage).compressBatch(PostActivity.this, newMessage);
                    PostActivity.this.finish();

//                    intent.putExtra("headline_return", myHeadline);
//                    intent.putExtra("context_return", myContext);
//                    //todo: images_return
//
//                    setResult(RESULT_OK, intent);
//                    finish();
                }
                else {
                    my_headline.setText("error");
                }
            }
        });

        mBtnAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, LabelActivity.class);
                startActivityForResult(intent,LABEL_ACTIVITY_REQUEST_CODE);
            }
        });

        mPhotosSnpl.setDelegate(this);
        mPhotosSnpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choicePhotoWrapper();
            }
        });
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }else if(requestCode == LABEL_ACTIVITY_REQUEST_CODE && resultCode == 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable ("Ulist",data.getSerializableExtra("list_1"));
            Message message = new Message();
            message.setData(bundle);
            message.what = 0;
            handler.sendMessage(message);
        }
        if(requestCode == LABEL_ACTIVITY_REQUEST_CODE && resultCode == 2){
            labelModelList = (List<LabelModel>) data.getSerializableExtra("clicked");
            initData();
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                uList =(List<User>) msg.getData().getSerializable("Ulist");
                for(User u:uList)
                {
                    System.out.println(u.getPhoneNum()+"b");
                }
            }
        }
    };

    void initData() {
        //设置默认显示
        for (LabelModel lamo : labelModelList) {
            TextView textView = new TextView(this);
            textView.setText(lamo.getTextValue());
            //设置背景
            textView.setBackground(getDrawable(R.drawable.bg_btn1));
            //设置内边距
            textView.setPadding(5,5,5,5);
            textView.setTextSize(20);
            //设置颜色
            textView.setTextColor(Color.GRAY);
            //添加数据
            customView.addView(textView);
        }
        //点击搜索添加

        //mSearchFlowlayout.invalidate();  刷新UI布局
        // mSearchFlowlayout.removeAllViews(); 删除所有
        //mSearchFlowlayout.removeView();   删除单个子控件
    }
}