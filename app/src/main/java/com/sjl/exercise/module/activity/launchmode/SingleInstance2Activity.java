package com.sjl.exercise.module.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class SingleInstance2Activity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_single_instance2;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_a3).setOnClickListener(v -> startActivity(new Intent(this, SingleInstance3Activity.class)));
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
