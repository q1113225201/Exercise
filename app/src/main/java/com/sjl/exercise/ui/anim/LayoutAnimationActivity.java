package com.sjl.exercise.ui.anim;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.sjl.exercise.R;
import com.sjl.exercise.adapter.TextAdapter;
import com.sjl.exercise.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class LayoutAnimationActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_layout_animation;
    }

    private RecyclerView recyclerView;
    private TextAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.btn_refresh).setOnClickListener(v -> {
            list.clear();
            loadData();
        });

        adapter = new TextAdapter(list, null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.item_anim);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        //延迟时间index * delay * animation duration
        layoutAnimationController.setDelay(0.2f);
        //执行顺序：顺序ORDER_NORMAL，倒序ORDER_REVERSE，随机ORDER_RANDOM
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        recyclerView.setLayoutAnimation(layoutAnimationController);
    }

    private void loadData() {
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }
        adapter.notifyDataSetChanged();
        //数据更新时执行
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void initData(Bundle bundle) {

    }
}
