package com.sjl.exercise;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.basic.BasicMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class MainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("Android 基础", BasicMainActivity.class));

    }
}
