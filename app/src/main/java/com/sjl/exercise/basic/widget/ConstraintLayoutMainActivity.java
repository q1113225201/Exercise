package com.sjl.exercise.basic.widget;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.basic.widget.ConstrainLayout.ConstrainLayoutBiasActivity;
import com.sjl.exercise.basic.widget.ConstrainLayout.ConstrainLayoutChainActivity;
import com.sjl.exercise.basic.widget.ConstrainLayout.ConstrainLayoutFillActivity;
import com.sjl.exercise.basic.widget.ConstrainLayout.ConstrainLayoutGuideLineActivity;
import com.sjl.exercise.basic.widget.ConstrainLayout.ConstrainLayoutRatioActivity;
import com.sjl.exercise.basic.widget.ConstrainLayout.ConstraintLayoutLocationActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class ConstraintLayoutMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("位置", ConstraintLayoutLocationActivity.class));
        list.add(new CatalogueBean("位置比例", ConstrainLayoutBiasActivity.class));
        list.add(new CatalogueBean("引导线", ConstrainLayoutGuideLineActivity.class));
        list.add(new CatalogueBean("填充", ConstrainLayoutFillActivity.class));
        list.add(new CatalogueBean("控件宽高比例", ConstrainLayoutRatioActivity.class));
        list.add(new CatalogueBean("链式", ConstrainLayoutChainActivity.class));
    }
}
