package com.sjl.exercise.basic;

import android.os.Bundle;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class BasicMainActivity extends BaseMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("控件", WidgetMainActivity.class));
    }
}
