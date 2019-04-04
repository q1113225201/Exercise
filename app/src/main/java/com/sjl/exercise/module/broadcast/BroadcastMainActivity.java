package com.sjl.exercise.module.broadcast;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.broadcast.send.BroadcastSendMainActivity;

public class BroadcastMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("广播种类", BroadcastSendMainActivity.class));
    }
}
