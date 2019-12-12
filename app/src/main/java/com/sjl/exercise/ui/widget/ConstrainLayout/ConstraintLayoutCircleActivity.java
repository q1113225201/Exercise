package com.sjl.exercise.ui.widget.ConstrainLayout;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ConstraintLayoutCircleActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_constraint_layout_circle;
    }

    FloatingActionButton fabMenu;
    FloatingActionButton fabPerson;
    FloatingActionButton fabMessage;
    FloatingActionButton fabStar;

    private boolean isOpen = false;
    private List<FloatingActionButton> menuList = new ArrayList<>();

    @Override
    public void initView() {
        fabMenu = findViewById(R.id.fab_menu);
        fabPerson = findViewById(R.id.fab_person);
        fabMessage = findViewById(R.id.fab_message);
        fabStar = findViewById(R.id.fab_star);

        fabMenu.setOnClickListener(v -> toggleMenu());
    }

    private void toggleMenu() {
        ValueAnimator valueAnimator;
        if (isOpen) {
            valueAnimator = ValueAnimator.ofInt(300, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, 300);
        }
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(animation -> {
            for (FloatingActionButton floatingActionButton : menuList) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) floatingActionButton.getLayoutParams();
                layoutParams.circleRadius = (int) animation.getAnimatedValue();
                floatingActionButton.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
        isOpen = !isOpen;
    }

    @Override
    public void initData(Bundle bundle) {
        menuList.add(fabPerson);
        menuList.add(fabMessage);
        menuList.add(fabStar);
    }
}
