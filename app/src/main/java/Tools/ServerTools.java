package Tools;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.chatting.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import AdaptObject.news;
import Bean.CommunityItem;
import Bean.MessageItem;
import Bean.User;
import Login.LoginAsync;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.Bmob.getCacheDir;
import static com.example.chatting.MyApplication.CurrentUser;
import static com.example.chatting.MyApplication.Threads;
import static com.example.chatting.MyApplication.mFTP;

public class ServerTools {
    /**
     * 暂名为服务器工具类。目前主要负责完成登录部分的所有功能（登录、注册、同步信息）
     * @param context 引用该类的上下文
     */
    private Context mcontext;
    private MessageItem messageItem;
    private CommunityItem communityItem;

    public void UserLogin(Context context) {
        CurrentUser.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    //登陆成功就跳转到主页面
                    Intent in_success = new Intent(context, MainActivity.class);
                    new ServerTools().UserInfoSync(context);
                    context.startActivity(in_success);
                }
                else {
                    UserSearch(context);
                }
            }
        });
    }

    public void UserSearch(Context context) {
        BmobQuery<User> q = new BmobQuery<User>();
        q.addWhereEqualTo("Username", CurrentUser.getUsername());
        q.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                new LoginAsync().getContext(context).executeOnExecutor(Threads);
            }
        });
    }

    public void UserSignUp(Context context, boolean ans){
        if (!ans) {
            Toast.makeText(context, "用户名或密码错误，也有可能是网络情况？请重试", Toast.LENGTH_SHORT).show();
            return ;
        }
        CurrentUser.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    //判断是否注册成功成功则跳转到登陆的页面
                    Intent intent_register = new Intent(context, MainActivity.class);
                    context.startActivity(intent_register);
                    Toast.makeText(context, "添加数据成功，返回objectId为：" + CurrentUser.getObjectId(), Toast.LENGTH_SHORT).show();
                    //这里我们需要了解一下什么objectid就像是一个登陆校验一样只要有这个就代表着你往表里面写入的数据是成功的了。
                }
                else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText(context, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 同步信息函数（将本地的用户信息同步至与服务器上的数据一致）
     * @param context 该活动所在的activity，用于发表提示信息
     */
    public void UserInfoSync(Context context) {
        CurrentUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "数据更新成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText(context, "数据更新失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 修改用户信息（在本地修改信息后，将该信息同步至服务器）
     * @param context 该活动所在的类，用于发表提示信息
     */
    public void UserInfoModify(Context context) {
        CurrentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "数据修改成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText(context, "数据修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void BeforeSaveMessage(Context context, MessageItem newMessage) {
        mcontext = context;
        messageItem = newMessage;
        ArrayList<String> images = newMessage.getCompressedImages();
        if (images == null || images.size() == 0) {
            SaveMessage(context, newMessage);
        }
        else {
            ArrayList<String> imageNames = new ArrayList<>();
            String baseName = "messageItem-" + BmobUser.getCurrentUser(User.class).getUsername() + "-" + mFTP.setDate();
            for (int i=0; i<images.size(); i++) {
                imageNames.add(i, baseName + "-" + i + "." + images.get(i).substring(images.get(i).lastIndexOf(".")+1));
            }
            messageItem.setImageNames(imageNames);
            new GraghAsync(images, imageNames, 0).executeOnExecutor(Threads);
        }
    }

    private void SaveMessage(Context context, MessageItem newMessage) {
        if (BmobUser.isLogin()) {
            newMessage.setAuthor(BmobUser.getCurrentUser(User.class));
            newMessage.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null)
                        Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
                    else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(context, "尚未登录", Toast.LENGTH_SHORT).show();
        }
    }

    public void BeforeDownLoadMessage(Context context, List<MessageItem> list) {
        Log.e("DownLoad:", "开始查询是否有文件需要下载");
        System.out.println("list长度：" + list.size());
        ArrayList<String> ImageNames = new ArrayList<>();
        for (MessageItem item : list) {
            ArrayList<String> names = item.getImageNames();
            if (names == null || names.size() == 0){

            }
            else {
                for (String name : names) {
                    System.out.println(getCacheDir().getPath() + "/" + name);
                    if (fileIsExists(getCacheDir().getPath() + "/" + name)) {
                        Log.e("DownLoad:", "查询地点：" + getCacheDir().getPath() + "/" + name);
                        ImageNames.add(name);
                    }
                }
            }
        }
        System.out.println("查询结束，共有"+ImageNames.size()+"文件需要下载");
        if (ImageNames.size() > 0) {
            System.out.println("进入图片下载");
            new GraghAsync(new ArrayList<>(), ImageNames, 1).executeOnExecutor(Threads);
        }
        else Log.e("DownLoad:", "没有文件需要下载");
    }

    public void BeforeSaveCommunityMessage(Context context, CommunityItem newMessage) {
        mcontext = context;
        communityItem = newMessage;
        ArrayList<String> images = newMessage.getCompressedImages();
        if (images == null || images.size() == 0) {
            SaveCommunityMessage(context, newMessage);
        }
        else {
            ArrayList<String> imageNames = new ArrayList<>();
            String baseName = "communityItem-" + BmobUser.getCurrentUser(User.class).getUsername() + "-" + mFTP.setDate();
            for (int i=0; i<images.size(); i++) {
                imageNames.add(i, baseName + "-" + i + "." + images.get(i).substring(images.get(i).lastIndexOf(".")+1));
            }
            communityItem.setImageNames(imageNames);
            new CommunityGraghAsync(images, imageNames, 0).executeOnExecutor(Threads);
        }
    }

    private void SaveCommunityMessage(Context context, CommunityItem newMessage) {
        if (BmobUser.isLogin()) {
            newMessage.setAuthor(BmobUser.getCurrentUser(User.class));
            newMessage.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null)
                        Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
                    else {
                        Log.e("BMOB", e.toString());
                        Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(context, "尚未登录", Toast.LENGTH_SHORT).show();
        }
    }

    public void BeforeDownLoadCommunityMessage(Context context, List<CommunityItem> list) {
        Log.e("DownLoad:", "开始查询是否有文件需要下载");
        System.out.println("list长度：" + list.size());
        ArrayList<String> ImageNames = new ArrayList<>();
        for (CommunityItem item : list) {
            ArrayList<String> names = item.getImageNames();
            if (names == null || names.size() == 0){

            } else {
                for (String name : names) {
                    System.out.println(getCacheDir().getPath() + "/" + name);
                    if (fileIsExists(getCacheDir().getPath() + "/" + name)) {
                        Log.e("DownLoad:", "查询地点：" + getCacheDir().getPath() + "/" + name);
                        ImageNames.add(name);
                    }
                }
            }
        }
        System.out.println("查询结束，共有"+ImageNames.size()+"文件需要下载");
        if (ImageNames.size() > 0) {
            System.out.println("进入图片下载");
            new CommunityGraghAsync(new ArrayList<>(), ImageNames, 1).executeOnExecutor(Threads);
        }
        else Log.e("DownLoad:", "没有文件需要下载");
    }

    public void MessageShow(List<news> newsList){
        if (BmobUser.isLogin()) {
            BmobQuery<MessageItem> q = new BmobQuery<>();
            q.addWhereEqualTo("author", BmobUser.getCurrentUser(User.class));
            q.order("-updatedAt");
            q.include("author");
            q.findObjects(new FindListener<MessageItem>() {
                //todo
                @Override
                public void done(List<MessageItem> list, BmobException e) {
                    if (e == null) {
                        for (int i = 0; i<list.size(); i++) {
                            MessageItem newMessage = list.get(i);
                            System.out.println(newMessage.getContent());
                            newsList.add(new news(newMessage.getTitle(), CurrentUser.getUsername(), newMessage.getContent(), newMessage.getImageNames()));
                        }
                        q.order("-updatedAt");
                    }
                }
            });
        }
    }

    private boolean fileIsExists(String strFile) {
        try {
            File f=new File(strFile);
            if(!f.exists()) {
                return true;
            }

        } catch (Exception e) {
            return true;
        }

        return false;
    }

    private class GraghAsync extends AsyncTask<Void, Boolean, Boolean> {
        private final String[] Urls;
        private final String[] Names;
        private final int mode;

        GraghAsync(ArrayList<String> images, ArrayList<String> imageNames, int mode) {
            Urls = new String[images.size()];
            Names = new String[imageNames.size()];

            for (int i=0; i<images.size(); i++) {
                Urls[i] = images.get(i);
            }
            for (int i=0; i<imageNames.size(); i++) {
                Names[i] = imageNames.get(i);
            }

            this.mode = mode;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean ans = true;
            if (mode == 0) {
                for (int i=0; i<Urls.length; i++) {
                    String url = Urls[i];
                    String name = Names[i];
                    ans = mFTP.uploadFile("/MessageItems", name, url) & ans;
                }
            } else {
                for (String name : Names) {
                    ans = mFTP.downloadFile("/MessageItems", name, getCacheDir().getPath());
                }
            }
            return ans;
        }

        @Override
        protected void onPostExecute(Boolean ans) {
            if (ans) {
                if (mode == 0) {
                    SaveMessage(mcontext, messageItem);
                }
                else Log.d("缓存：", getCacheDir().getPath());
            }
        }
    }

    private class CommunityGraghAsync extends AsyncTask<Void, Boolean, Boolean> {

        private final String[] Urls;
        private final String[] Names;
        private final int mode;

        CommunityGraghAsync(ArrayList<String> images, ArrayList<String> imageNames, int mode) {
            Urls = new String[images.size()];
            Names = new String[imageNames.size()];

            for (int i=0; i<images.size(); i++) {
                Urls[i] = images.get(i);
            }
            for (int i=0; i<imageNames.size(); i++) {
                Names[i] = imageNames.get(i);
            }

            this.mode = mode;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean ans = true;
            if (mode == 0) {
                for (int i=0; i<Urls.length; i++) {
                    String url = Urls[i];
                    String name = Names[i];
                    ans = mFTP.uploadFile("/CommunityItems", name, url) & ans;
                }
            } else {
                for (String name : Names) {
                    ans = mFTP.downloadFile("/CommunityItems", name, getCacheDir().getPath());
                }
            }
            return ans;
        }

        @Override
        protected void onPostExecute(Boolean ans) {
            if (ans) {
                if (mode == 0) {
                    SaveCommunityMessage(mcontext, communityItem);
                }
                else Log.d("缓存：", getCacheDir().getPath());
            }
        }
    }
}
