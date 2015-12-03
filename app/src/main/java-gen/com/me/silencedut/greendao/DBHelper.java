package com.me.silencedut.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SilenceDut on 2015/12/3.
 */
public class DBHelper {

    private static DBHelper sInstance;
    private static final Object WATCH_DOG=new Object();
    private static final String DB_NAME = "nbaplus";
    private DaoSession daoSession;
    private SQLiteDatabase db;
    public static DBHelper getInstance(Context context) {
        synchronized (WATCH_DOG) {
            if(sInstance==null) {
                sInstance=new DBHelper(context);
            }
            return sInstance;
        }
    }

    private DBHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


}
