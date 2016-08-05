package com.oscar.festivelsms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.oscar.festivelsms.bean.SendedMsg;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class SmsDBHelper extends SQLiteOpenHelper  {
    private static final String DB_NAME = "sms.db";//数据库名
    private static final int DB_VERSION = 1;//数据库版本

    /**
     * 单例模式获取数据库对象
     */
    private static SmsDBHelper mHelper;

    private SmsDBHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    public static SmsDBHelper getInstance(Context context) {
        if(mHelper == null) {
            synchronized (SmsDBHelper.class) {
                if(mHelper == null) {
                    mHelper = new SmsDBHelper(context);
                }
            }
        }
        return mHelper;
    }

    /**
     * 创建数据库表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + SendedMsg.TABLE_NAME + " ( " +
                "_id integer primary key autoincrement, " +
                SendedMsg.COLUMN_DATE + " integer, " +
                SendedMsg.COLUMN_MSG + " text, " +
                SendedMsg.COLUMN_NUMBERS + " text, " +
                SendedMsg.COLUMN_NAMES + " text, " +
                SendedMsg.COLUMN_FESTIVALNAME + " text " +
                " ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
