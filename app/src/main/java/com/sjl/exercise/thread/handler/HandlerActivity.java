package com.sjl.exercise.thread.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

import java.lang.ref.WeakReference;

public class HandlerActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_handler;
    }

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };

    private static class MyHandler extends Handler {
        private WeakReference<HandlerActivity> weakReference;

        public MyHandler(HandlerActivity handlerActivity) {
            this.weakReference = new WeakReference<>(handlerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerActivity activity = weakReference.get();
            if (activity != null) {
                activity.tvMessage.setText(msg.obj.toString());
            }
        }
    }

    private MyHandler myHandler;
    TextView tvMessage;

    @Override
    public void initView() {
        myHandler = new MyHandler(this);
        tvMessage = findViewById(R.id.tv_message);
        findViewById(R.id.btn_handler_send).setOnClickListener(v -> {
            Message message = myHandler.obtainMessage();
            message.obj = "send message";
            myHandler.sendMessageDelayed(message, 2 * 1000);
        });
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }
}
