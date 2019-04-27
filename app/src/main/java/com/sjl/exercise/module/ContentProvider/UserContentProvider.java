package com.sjl.exercise.module.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class UserContentProvider extends ContentProvider {
    private static final String AUTHORITIES = "com.sjl.provider";
    private static final String PATH = "user";
    private static final int CODE = 1;
    private Context mContext;
    private UserDBHelper mUserDBHelper;
    private SQLiteDatabase db;
    private UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mUserDBHelper = new UserDBHelper(mContext);
        db = mUserDBHelper.getWritableDatabase();

        mUriMatcher.addURI(AUTHORITIES, PATH, CODE);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (isMatch(uri)) {
            return db.delete(UserDBHelper.TABLE_USER, selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (isMatch(uri)) {
            long id = db.insert(UserDBHelper.TABLE_USER, null, values);
            return ContentUris.withAppendedId(uri, id);
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (isMatch(uri)) {
            return db.query(UserDBHelper.TABLE_USER, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (isMatch(uri)) {
            return db.update(UserDBHelper.TABLE_USER, values, selection, selectionArgs);
        }
        return 0;
    }

    /**
     * 是否是提供的数据访问
     * @param uri
     * @return
     */
    private boolean isMatch(Uri uri) {
        return CODE == mUriMatcher.match(uri);
    }
}
