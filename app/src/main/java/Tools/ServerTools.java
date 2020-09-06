package Tools;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.chatting.MainActivity;

import java.io.IOException;
import java.util.List;

import AdaptObject.news;
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

import static com.example.chatting.MyApplication.CurrentUser;
import static com.example.chatting.MyApplication.Threads;

public class ServerTools {
    /**
     * 暂名为服务器工具类。目前主要负责完成登录部分的所有功能（登录、注册、同步信息）
     * @param context 引用该类的上下文
     */

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

    public void UserSignUp(Context context, boolean ans) throws IOException {
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

    public void SaveMessage(Context context, MessageItem newMessage) {
        System.out.println("开始上传");
        if (BmobUser.isLogin()) {
            System.out.println("正在上传");
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
                            newsList.add(new news(newMessage.getTitle(), CurrentUser.getUsername(), newMessage.getContent()));
                        }
                        q.order("-updatedAt");
                    }
                }
            });
        }
    }
}
