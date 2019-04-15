package com.sjl.exercise.basic.custom.NewsView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsViewActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_news_view;
    }

    NewsView newsView;

    @Override
    public void initView() {
        newsView = findViewById(R.id.news_view);
    }

    @Override
    public void initData(Bundle bundle) {
        List<String> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add("item"+i);
        }
        newsView.setData(list);
    }
}
