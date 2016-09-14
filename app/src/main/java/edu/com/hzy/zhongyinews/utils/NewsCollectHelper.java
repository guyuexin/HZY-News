package edu.com.hzy.zhongyinews.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class NewsCollectHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "collectnews.db";

    public NewsCollectHelper(Context context) {
        super(context, DB_NAME, null, 4);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table CollTbl(_id integer primary key autoincrement,name text not null,url text not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS CollTbl");
        onCreate(db);
    }
}
