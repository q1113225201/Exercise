package com.sjl.exercise.module.service.foreground;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;
import com.sjl.exercise.module.service.foreground.ForegroundService;

public class ForegroundServiceActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_foreground_service;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            Intent intent = new Intent(this, ForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(v -> stopService(new Intent(this, ForegroundService.class)));
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
