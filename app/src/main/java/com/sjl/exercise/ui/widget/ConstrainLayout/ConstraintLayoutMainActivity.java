package com.sjl.exercise.ui.widget.ConstrainLayout;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class ConstraintLayoutMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("相对定位", ConstraintLayoutLocationActivity.class));
        list.add(new CatalogueBean("角度定位", ConstraintLayoutCircleActivity.class));
        list.add(new CatalogueBean("比例定位", ConstrainLayoutBiasActivity.class));
        list.add(new CatalogueBean("引导线定位", ConstrainLayoutGuideLineActivity.class));
        list.add(new CatalogueBean("填充", ConstrainLayoutFillActivity.class));
        list.add(new CatalogueBean("控件宽高比例", ConstrainLayoutRatioActivity.class));
        list.add(new CatalogueBean("链式", ConstrainLayoutChainActivity.class));
    }
}
