package com.example.chatting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by can on 2018/2/26.
 * 消息数据库操作
 */

public class MessageDataOpenHelper extends SQLiteOpenHelper {


    public MessageDataOpenHelper(Context context) {
        super(context, "message.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //noeID : 某一条消息的id
        //noeTitle : 消息的标题
        //noeTime : 消息的时间
        // isRead ： 0表示未读 1表示已读
        db.execSQL("create table if not exists message(id integer primary key autoincrement ,noeTitle varchar(50), isRead varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
