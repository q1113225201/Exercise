package com.sjl.exercise.basic.widget;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.basic.widget.ConstrainLayout.ConstraintLayoutMainActivity;
import com.sjl.exercise.basic.widget.RecyclerView.RecyclerViewActivity;
import com.sjl.exercise.basic.widget.ViewFlipper.ViewFlipperActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class WidgetMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("ConstraintLayout", ConstraintLayoutMainActivity.class));
        list.add(new CatalogueBean("ViewFlipper", ViewFlipperActivity.class));
        list.add(new CatalogueBean("RecyclerView", RecyclerViewActivity.class));
    }
}
