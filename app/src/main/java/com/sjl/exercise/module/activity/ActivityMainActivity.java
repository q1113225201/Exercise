package com.sjl.exercise.module.activity;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.activity.correspondence.ActivityCorrespondenceActivity;
import com.sjl.exercise.module.activity.launchmode.LaunchModeMainActivity;
import com.sjl.exercise.module.activity.screen.ScreenConfigActivity;

public class ActivityMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("启动模式 launchMode", LaunchModeMainActivity.class));
        list.add(new CatalogueBean("Activity通信", ActivityCorrespondenceActivity.class));
        list.add(new CatalogueBean("横竖屏", ScreenConfigActivity.class));
    }
}
