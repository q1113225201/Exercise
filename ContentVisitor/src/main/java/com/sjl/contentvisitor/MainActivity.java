package com.sjl.contentvisitor;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    TextView tvResult;

    private Uri mUri = Uri.parse("content://com.sjl.provider/user");
    private ContentResolver contentResolver;
    private int cnt = 0;

    private void initView() {
        tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_insert).setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "visitor" + cnt);
            contentValues.put("age", cnt);
            contentValues.put("address", "visitor address" + cnt);
            Uri uri = contentResolver.insert(mUri, contentValues);
            cnt++;
            tvResult.setText(uri.toString());
        });
        findViewById(R.id.btn_delete).setOnClickListener(v -> {
            int rowsNum = contentResolver.delete(mUri, "name like ?", new String[]{"visitor%"});
            tvResult.setText(String.format("删除数据%d条", rowsNum));
        });
        findViewById(R.id.btn_update).setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("address", "visitor address update");
            int rowsNum = contentResolver.update(mUri, contentValues, "name like ?", new String[]{"visitor%"});
            tvResult.setText(String.format("修改数据%d条", rowsNum));
        });
        findViewById(R.id.btn_query).setOnClickListener(v -> {
            tvResult.setText("");
            Cursor cursor = contentResolver.query(mUri, new String[]{"id", "name", "age", "address"}, null, null, null);
            while (cursor.moveToNext()) {
                tvResult.append(String.format("id=%d,name=%s,age=%d,address=%s\n",
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                ));
            }
            cursor.close();
        });

        contentResolver = getContentResolver();
    }
}
