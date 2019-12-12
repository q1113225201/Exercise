package com.sjl.exercise.ui.view;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.ui.view.conflict.BasicViewConflictMainActivity;
import com.sjl.exercise.ui.view.scroll.BasicViewScrollActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class BasicViewMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("滑动", BasicViewScrollActivity.class));
        list.add(new CatalogueBean("滑动冲突", BasicViewConflictMainActivity.class));
    }
}
