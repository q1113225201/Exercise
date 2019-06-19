package com.sjl.exercise.module;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.activity.ActivityMainActivity;
import com.sjl.exercise.module.broadcast.BroadcastMainActivity;
import com.sjl.exercise.module.other.OtherMainActivity;
import com.sjl.exercise.module.service.ServiceMainActivity;

public class ModuleMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("Activity", ActivityMainActivity.class));
        list.add(new CatalogueBean("Broadcast", BroadcastMainActivity.class));
        list.add(new CatalogueBean("Service", ServiceMainActivity.class));
        list.add(new CatalogueBean("其他", OtherMainActivity.class));
    }
}
