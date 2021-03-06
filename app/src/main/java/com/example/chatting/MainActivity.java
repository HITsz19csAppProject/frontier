package com.example.chatting;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lzy.ninegrid.NineGridView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private long exitTime = 0;
    private Handler mHandler = new Handler();
    private Runnable mFinish = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            MyApplication.getInstance().addActivity(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.hide();
            }
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_notification, R.id.navigation_chatting, R.id.navigation_center)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
            NineGridView.setImageLoader(new GlideImageLoader());
        }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((System.currentTimeMillis()-exitTime) > 2000){
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            MyApplication.getInstance().exit();
            //mHandler.postDelayed(mFinish, 0);
        }
        return false;
    }

    /** Glide 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.drawable.ic_default_image)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

}
