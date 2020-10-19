package com.example.chatting;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程下调用SQLite
 */
public class DataBaseManager {
    //解决多线程并发
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DataBaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    private DataBaseManager(){}

    /**
     * 初始化
     */
    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DataBaseManager();
            mDatabaseHelper = helper;
        }
    }

    /**
     * 获得当前实例对象
     */
    public static synchronized DataBaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    DataBaseManager.class.getSimpleName()
                            + " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    /**
     * 打开数据库对象
     */
    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * 多线程下关闭
     */
    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }
}
