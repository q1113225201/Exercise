package com.sjl.exercise.ui.widget.RecyclerView;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sjl.exercise.R;
import com.sjl.exercise.adapter.TextAdapter;
import com.sjl.exercise.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_recycler_view;
    }

    RecyclerView recyclerView;
    TextAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new TextAdapter(list, (view, position) ->
                Toast.makeText(getApplication(), list.get(position), Toast.LENGTH_SHORT).show());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData(Bundle bundle) {
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }
        adapter.notifyDataSetChanged();
    }
}
