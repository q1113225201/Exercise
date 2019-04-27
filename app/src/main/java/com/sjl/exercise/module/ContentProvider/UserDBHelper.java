package com.sjl.exercise.module.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * UserDBHelper
 *
 * @author 林zero
 * @date 2019/4/27
 */
public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sjl.db";
    public static final String TABLE_USER = "user";
    private static final int VERSION = 1;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT," +
                "age INTEGER," +
                "address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
