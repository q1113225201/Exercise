package com.sjl.exercise.module.broadcast.send;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class BroadcastSendMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("有序广播", BroadcastSendOrderActivity.class));
    }
}
