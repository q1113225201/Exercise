package com.sjl.exercise.basic.widget.ViewFlipper;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class ViewFlipperActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_view_flipper;
    }

    ViewFlipper viewFlipper;

    @Override
    public void initView() {
        viewFlipper = findViewById(R.id.view_flipper);
    }

    @Override
    public void initData(Bundle bundle) {
        initFlipper();
    }

    private void initFlipper() {
        for (int i = 0; i < 5; i++) {
            View view = View.inflate(this, R.layout.item_news, null);
            ((TextView) view.findViewById(R.id.tv_content)).setText("item" + i);
            viewFlipper.addView(view);
        }
        //进出动画
        viewFlipper.setInAnimation(this, R.anim.in_bottom);
        viewFlipper.setOutAnimation(this, R.anim.out_top);
        //时间间隔
        viewFlipper.setFlipInterval(2000);
        //自动开始
        viewFlipper.setAutoStart(true);
    }
}
