package com.sjl.exercise.module.other.thread;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.module.other.thread.AsyncTask.AsyncTaskActivity;
import com.sjl.exercise.module.other.thread.HandlerThread.HandlerThreadActivity;
import com.sjl.exercise.module.other.thread.handler.HandlerActivity;

public class ThreadMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("Handler", HandlerActivity.class));
        list.add(new CatalogueBean("HandlerThread", HandlerThreadActivity.class));
        list.add(new CatalogueBean("AsyncTask", AsyncTaskActivity.class));
    }
}
