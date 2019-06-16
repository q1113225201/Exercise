package com.sjl.exercise.basic;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.basic.custom.CustomWidgetActivity;
import com.sjl.exercise.basic.remote.AppWidgetProviderActivity;
import com.sjl.exercise.basic.view.BasicViewActivity;
import com.sjl.exercise.basic.widget.WidgetMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.ModuleMainActivity;

public class BasicMainActivity extends BaseMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("组件", ModuleMainActivity.class));
        list.add(new CatalogueBean("控件", WidgetMainActivity.class));
        list.add(new CatalogueBean("自定义控件", CustomWidgetActivity.class));
        list.add(new CatalogueBean("View基础", BasicViewActivity.class));
        list.add(new CatalogueBean("桌面小部件", AppWidgetProviderActivity.class));
    }
}
