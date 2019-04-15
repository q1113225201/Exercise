package com.sjl.exercise.module.service.IntentService;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class IntentServiceActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_intent_service;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_action1).setOnClickListener(v->{
            Intent intent = new Intent(IntentServiceActivity.this,CustomIntentService.class);
            intent.setAction(CustomIntentService.ACTION1);
            intent.putExtra(CustomIntentService.DATA,"data-action1");
            startService(intent);
        });
        findViewById(R.id.btn_action2).setOnClickListener(v->{
            Intent intent = new Intent(IntentServiceActivity.this,CustomIntentService.class);
            intent.setAction(CustomIntentService.ACTION2);
            intent.putExtra(CustomIntentService.DATA,"data-action2");
            startService(intent);
        });
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
