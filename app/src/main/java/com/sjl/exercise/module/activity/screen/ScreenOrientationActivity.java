package com.sjl.exercise.module.activity.screen;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class ScreenOrientationActivity extends BaseActivity {
    private static final String TAG = "ScreenOrientation";

    @Override
    public int getContentLayout() {
        return R.layout.activity_screen_orientation;
    }

    private int cnt = 0;
    TextView tvText;

    @Override
    public void initView() {
        tvText = findViewById(R.id.tv_text);
        findViewById(R.id.btn_count).setOnClickListener(v -> tvText.setText(String.format("cnt=" + ++cnt)));
        findViewById(R.id.btn_toggle).setOnClickListener(v->{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        tvText.setText(String.format("当前：%s", newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "竖屏" : "横屏"));
        Log.i(TAG, tvText.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(TAG, cnt);
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvText.setText(String.format("onRestoreInstanceState 记录数据：%d", savedInstanceState.getInt(TAG)));
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
