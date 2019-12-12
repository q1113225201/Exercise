package com.sjl.exercise.ui.remote;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class AppWidgetProviderActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_app_widget_provider;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_update).setOnClickListener(v-> sendBroadcast(new Intent(MyAppWidget.ACTION_CLICK)));
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
