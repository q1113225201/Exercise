package com.sjl.exercise.ui.view.conflict;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sjl.exercise.R;
import com.sjl.exercise.adapter.TextAdapter;
import com.sjl.exercise.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BasicViewConflict2Activity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_basic_view_conflict2;
    }

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private TextAdapter adapter;
    private TextAdapter adapter2;
    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView2 = findViewById(R.id.recycler_view2);

    }

    @Override
    public void initData(Bundle bundle) {
        initAdapter();
    }

    private void initAdapter() {
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
            list2.add("list2 item" + i);
        }
        adapter = new TextAdapter(list, (view, position) -> {

        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter2 = new TextAdapter(list2, (view, position) -> {

        });
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
    }
}
