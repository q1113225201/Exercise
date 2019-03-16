package com.sjl.exercise.basic.widget;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class ConstraintLayoutMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("位置", ConstraintLayoutLocationActivity.class));
    }
}
