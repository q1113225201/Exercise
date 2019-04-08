package com.sjl.exercise.module.service;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.service.foreground.ForegroundServiceActivity;
import com.sjl.exercise.module.service.remote.RemoteServiceActivity;

public class ServiceMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("前台服务", ForegroundServiceActivity.class));
        list.add(new CatalogueBean("远程服务", RemoteServiceActivity.class));
    }
}
