package com.sjl.exercise.thread.HandlerThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class HandlerThreadActivity extends BaseActivity {
    private static final String TAG = "HandlerThreadActivity";

    @Override
    public int getContentLayout() {
        return R.layout.activity_handler_thread;
    }

    private TextView tvResult;

    private HandlerThread handlerThread;
    private Handler childHandler;
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "uiHandler handleMessage:" + msg.obj.toString());
            tvResult.setText(msg.obj.toString());
        }
    };

    @Override
    public void initView() {
        tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_start).setOnClickListener(v -> start());
    }

    private int cnt = 0;

    private void start() {
        Log.i(TAG, "childHandler send");
        Message message = new Message();
        message.obj = String.format("第%d次操作", cnt++);
        childHandler.sendMessage(message);
    }

    @Override
    public void initData(Bundle bundle) {
        //创建HandlerThread
        handlerThread = new HandlerThread("handlerThread");
        //开启线程
        handlerThread.start();
        //获取子线程
        childHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                int count = 0;
                while (count < 5) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.obj = String.format("%s----%d",msg.obj.toString(),count++);
                    uiHandler.sendMessage(message);
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
