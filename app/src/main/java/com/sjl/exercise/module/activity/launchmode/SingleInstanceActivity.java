package com.sjl.exercise.module.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class SingleInstanceActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_single_instance;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_scene1).setOnClickListener(v -> startActivity(new Intent(this, SingleInstance3Activity.class)));
        findViewById(R.id.btn_scene2).setOnClickListener(v -> startActivity(new Intent(this, SingleInstance2Activity.class)));
        findViewById(R.id.btn_scene3).setOnClickListener(v -> startActivity(new Intent(this, SingleInstance2Activity.class)));
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
