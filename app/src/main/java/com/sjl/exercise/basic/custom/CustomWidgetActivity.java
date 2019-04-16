package com.sjl.exercise.basic.custom;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.basic.custom.NewsView.NewsViewActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class CustomWidgetActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("滚动新闻", NewsViewActivity.class));
    }
}