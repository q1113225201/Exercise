package com.sjl.exercise.module;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.activity.ActivityMainActivity;
import com.sjl.exercise.module.broadcast.BroadcastMainActivity;

public class ModuleMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("Activity", ActivityMainActivity.class));
        list.add(new CatalogueBean("Broadcast", BroadcastMainActivity.class));
    }
}
