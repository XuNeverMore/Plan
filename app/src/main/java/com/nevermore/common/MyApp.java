package com.nevermore.common;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.nevermore.bean.DaoMaster;
import com.nevermore.bean.DaoSession;
import com.nevermore.bean.RecordDao;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MyApp extends Application {
    private static MyApp instance;
    private RecordDao recordDao;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"record.db");
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        recordDao = daoSession.getRecordDao();

    }

    public static MyApp getInstance(){
        return instance;
    }

    public RecordDao getRecordDao(){
        return recordDao;
    }
}
