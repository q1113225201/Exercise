package com.sjl.exercise.ui.anim;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;
import com.sjl.exercise.ui.anim.custom.Rotate3DAnim;

public class Rotate3dActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_rotate3d;
    }

    private ImageView iv;

    @Override
    public void initView() {
        iv = findViewById(R.id.iv);
        findViewById(R.id.btn_rotate).setOnClickListener(v -> startRotate());
    }

    private void startRotate() {
        Rotate3DAnim rotate3DAnim = new Rotate3DAnim(0,360, iv.getMeasuredWidth() / 2, iv.getMeasuredHeight() / 2, 0, false);
        rotate3DAnim.setDuration(2000);
        rotate3DAnim.setFillAfter(true);
        rotate3DAnim.setFillBefore(true);
        iv.startAnimation(rotate3DAnim);
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
