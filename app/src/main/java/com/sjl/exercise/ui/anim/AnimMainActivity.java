package com.sjl.exercise.ui.anim;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;

public class AnimMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("3D旋转", Rotate3dActivity.class));
        list.add(new CatalogueBean("LayoutAnimation", LayoutAnimationActivity.class));
    }
}
