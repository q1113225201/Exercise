package com.sjl.exercise.ui.window;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class WindowActivity extends BaseActivity {
    private static final String TAG = "WindowActivity";

    @Override
    public int getContentLayout() {
        return R.layout.activity_window;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_permission).setOnClickListener(v -> request());
        findViewById(R.id.btn_add).setOnClickListener(v -> add());
        findViewById(R.id.btn_update).setOnClickListener(v -> update());
        findViewById(R.id.btn_remove).setOnClickListener(v -> remove());
    }

    private void remove() {
        for (View view : list) {
            windowManager.removeView(view);
        }
    }

    private void update() {
        for (View view : list) {
            ((Button) view).setText("update-" + ((Button) view).getText());
            windowManager.updateViewLayout(view, view.getLayoutParams());
        }
    }

    private void request() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    private int cnt = 0;

    private void add() {
        Button button = new Button(this);
        button.setText("btn" + cnt++);
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) v.getLayoutParams();
                    layoutParams.x = (int) event.getRawX();
                    layoutParams.y = (int) event.getRawY();
                    windowManager.updateViewLayout(v, layoutParams);
                    Log.e(TAG, event.getRawX() + "," + event.getRawY());
                    break;
            }
            return false;
        });


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, 0, PixelFormat.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManager.addView(button, layoutParams);
        list.add(button);
    }

    private WindowManager windowManager;
    private List<View> list = new ArrayList<>();

    @Override
    public void initData(Bundle bundle) {
        windowManager = getWindowManager();
    }
}
