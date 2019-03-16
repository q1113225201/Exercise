package com.sjl.exercise.basic;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.basic.widget.ConstraintLayoutMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class WidgetMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("ConstraintLayout", ConstraintLayoutMainActivity.class));
    }
}
