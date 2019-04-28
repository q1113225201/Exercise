package com.sjl.exercise;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.basic.BasicMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.function.FunctionMainActivity;
import com.sjl.exercise.process.ProcessMainActivity;
import com.sjl.exercise.thread.ThreadMainActivity;

public class MainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("Android 基础", BasicMainActivity.class));
        list.add(new CatalogueBean("功能", FunctionMainActivity.class));
        list.add(new CatalogueBean("线程", ThreadMainActivity.class));
        list.add(new CatalogueBean("进程", ProcessMainActivity.class));

    }
}
