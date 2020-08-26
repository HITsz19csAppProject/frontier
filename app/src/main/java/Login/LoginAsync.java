package Login;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import Tools.ServerTools;

import static com.example.chatting.MyApplication.UserInfo;

public class LoginAsync extends AsyncTask<Void, Void, Boolean> {
    private Context context;

    public LoginAsync getContext(Context context) {
        this.context = context;
        return this;
    }

    /**
     * 异步登录的后台进程，执行教务网认证的任务
     * @param voids
     * @return 若认证成功，返回true, 否则返回false
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            return new LoginCore().Login(UserInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * UI进程中的进程，执行下一步任务（若认证成功：注册，否则：报错）
     * @param result 后台进程的返回值（认证是否成功）
     */
    @Override
    protected void onPostExecute(Boolean result) {
        try {
            new ServerTools().UserSignUp(context, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
