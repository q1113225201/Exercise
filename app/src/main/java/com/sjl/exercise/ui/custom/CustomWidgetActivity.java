package com.sjl.exercise.ui.custom;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.ui.custom.NewsView.NewsViewActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.ui.custom.ShadowLayout.ShadowLayoutActivity;

public class CustomWidgetActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("滚动新闻", NewsViewActivity.class));
        list.add(new CatalogueBean("阴影Layout", ShadowLayoutActivity.class));
    }
}
