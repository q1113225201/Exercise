package com.sjl.exercise.module.activity.launchmode;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class LaunchModeMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("singleInstance", SingleInstanceActivity.class));
    }
}
