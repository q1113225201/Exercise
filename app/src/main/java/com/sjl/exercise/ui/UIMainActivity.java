package com.sjl.exercise.ui;

import android.os.Bundle;
import android.view.WindowManager;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.ui.anim.AnimMainActivity;
import com.sjl.exercise.ui.custom.CustomWidgetActivity;
import com.sjl.exercise.ui.remote.AppWidgetProviderActivity;
import com.sjl.exercise.ui.view.BasicViewMainActivity;
import com.sjl.exercise.ui.widget.WidgetMainActivity;
import com.sjl.exercise.ui.window.WindowActivity;

public class UIMainActivity extends BaseMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("控件", WidgetMainActivity.class));
        list.add(new CatalogueBean("自定义控件", CustomWidgetActivity.class));
        list.add(new CatalogueBean("桌面小部件", AppWidgetProviderActivity.class));
        list.add(new CatalogueBean("View基础", BasicViewMainActivity.class));
        list.add(new CatalogueBean("动画", AnimMainActivity.class));
        list.add(new CatalogueBean("Window", WindowActivity.class));
    }
}
