package com.sjl.exercise.module.other.process;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.service.remote.RemoteServiceActivity;
import com.sjl.exercise.module.other.process.messenger.MessengerActivity;

public class ProcessMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("进程间通信AIDL", RemoteServiceActivity.class));
        list.add(new CatalogueBean("进程间通信Messenger", MessengerActivity.class));
    }
}
