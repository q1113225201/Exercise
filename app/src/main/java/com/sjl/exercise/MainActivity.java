package com.sjl.exercise;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.function.FunctionMainActivity;
import com.sjl.exercise.module.ModuleMainActivity;
import com.sjl.exercise.ui.UIMainActivity;

public class MainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("界面UI", UIMainActivity.class));
        list.add(new CatalogueBean("组件", ModuleMainActivity.class));
        list.add(new CatalogueBean("功能", FunctionMainActivity.class));

    }
}
