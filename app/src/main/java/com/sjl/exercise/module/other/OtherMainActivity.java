package com.sjl.exercise.module.other;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.other.process.ProcessMainActivity;
import com.sjl.exercise.module.other.thread.ThreadMainActivity;

public class OtherMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("线程", ThreadMainActivity.class));
        list.add(new CatalogueBean("进程", ProcessMainActivity.class));
    }
}
