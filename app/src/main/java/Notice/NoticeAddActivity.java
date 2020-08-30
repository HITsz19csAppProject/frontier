package Notice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatting.NoticeAdapter;
import com.example.chatting.R;

import java.util.List;

import Bean.MessageItem;
import Bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.disposables.Disposable;

import static com.example.chatting.R.id.content;

public class NoticeAddActivity extends Activity {
    EditText content,title;
    User user = BmobUser.getCurrentUser(User.class);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);
        // Bmob.initialize(this,);
        content=(EditText) findViewById(R.id.content);
        title=(EditText)findViewById(R.id.title);

    }

    public void post(View view){
        String mtitile=title.getText().toString();
        String mcontent=content.getText().toString();
        if(mtitile == null){
            // Toast.makeText().show();
        }else{
            MessageItem notice=new MessageItem();
            notice.setAuthor(user)
                    .setContent(mcontent)
                    .setTitle(mtitile)
                    .save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null){
                                Toast.makeText(NoticeAddActivity.this,"添加数据成功",Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(NoticeAddActivity.this,"创建数据失败",Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }


}