package com.sjl.exercise.ui.view.conflict;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class BasicViewConflictMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("水平垂直不同向冲突", BasicViewConflict1Activity.class));
        list.add(new CatalogueBean("垂直同向冲突", BasicViewConflict2Activity.class));
        list.add(new CatalogueBean("水平垂直同向不同向嵌套冲突", BasicViewConflict3Activity.class));
    }
}
