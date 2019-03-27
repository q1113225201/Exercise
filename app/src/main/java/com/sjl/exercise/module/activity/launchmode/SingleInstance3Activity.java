package com.sjl.exercise.module.activity.launchmode;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class SingleInstance3Activity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_single_instance3;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_start).setOnClickListener(v->startActivity(new Intent(this,SingleInstance4Activity.class)));
        findViewById(R.id.btn_start_for_result).setOnClickListener(v->startActivityForResult(new Intent(this,SingleInstance4Activity.class),1));
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
