package com.sjl.exercise.function;

import android.os.Bundle;

import com.sjl.exercise.base.BaseMainActivity;
import com.sjl.exercise.bean.CatalogueBean;
import com.sjl.exercise.function.picture.FunctionPictureActivity;
import com.sjl.exercise.function.record.RecordActivity;

public class FunctionMainActivity extends BaseMainActivity {

    @Override
    public void initData(Bundle bundle) {
        list.add(new CatalogueBean("图片选择", FunctionPictureActivity.class));
        list.add(new CatalogueBean("录音", RecordActivity.class));
    }
}
