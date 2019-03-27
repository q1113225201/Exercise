package com.sjl.exercise.module.activity.screen;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class ScreenConfigActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_screen_config;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_orientation).setOnClickListener(v -> startActivity(new Intent(this, ScreenOrientationActivity.class)));
        findViewById(R.id.btn_screen_size).setOnClickListener(v -> startActivity(new Intent(this, ScreenSizeActivity.class)));
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
