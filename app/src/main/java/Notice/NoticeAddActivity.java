package Notice;

import Bean.MessageItem;
import Bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class NoticeAddActivity {
    private String NoticeContent;
    private String NoticeTitle;

    public void NoticePublish(){
        MessageItem bean = new MessageItem();
        User user = BmobUser.getCurrentUser(User.class);
        bean.setAuthor(user)
                .setContent(NoticeContent)
                .setTitle(NoticeTitle)
                .save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
//                            Toast.makeText(NoticeAddActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
