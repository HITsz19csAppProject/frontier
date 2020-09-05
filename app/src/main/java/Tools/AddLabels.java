package Tools;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.chatting.AnnounceActivity;
import com.example.chatting.LabelActivity;
import com.example.chatting.LabelLayoutView;
import com.example.chatting.LabelOnClicked;

import java.util.ArrayList;
import java.util.List;

import Bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddLabels extends Application {
    public ArrayList<String> grade = new ArrayList<>();        //年级
    public ArrayList<String> Class = new ArrayList<>();        //班级
    public ArrayList<String> academy = new ArrayList<>();      //学院
    public ArrayList<String> speciality = new ArrayList<>();   //专业

    public void addLabels()
    {
        BmobQuery<User> userGrade = new BmobQuery<User>();
        userGrade.addWhereContainedIn("Grade",grade);

        BmobQuery<User> userClass = new BmobQuery<User>();
        userGrade.addWhereContainedIn("ClassID",Class);

        BmobQuery<User> useracademy = new BmobQuery<User>();
        useracademy.addWhereContainedIn("",academy);

        BmobQuery<User> userSpeciality = new BmobQuery<User>();
        userGrade.addWhereContainedIn("Major",speciality);

        List<BmobQuery<User>> andQuerys = new ArrayList<BmobQuery<User>>();
        andQuerys.add(userGrade);
        andQuerys.add(userClass);
        andQuerys.add(useracademy);
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
                    System.out.println(query.toString()+"cehnggong");
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());//发生错误，返回错误代码
                }
            }
        });
    }
}
