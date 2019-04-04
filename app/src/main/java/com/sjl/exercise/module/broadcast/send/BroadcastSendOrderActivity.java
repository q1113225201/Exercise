package com.sjl.exercise.module.broadcast.send;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class BroadcastSendOrderActivity extends BaseActivity {
    private static final String DATA = "data";

    @Override
    public int getContentLayout() {
        return R.layout.activity_broadcast_send_order;
    }

    TextView tvReceiver;
    ToggleButton tbOpen;

    @Override
    public void initView() {
        tvReceiver = findViewById(R.id.tv_receiver);
        tbOpen = findViewById(R.id.tb_open);
        findViewById(R.id.btn_send_action1).setOnClickListener(v -> sendAction1());
        findViewById(R.id.btn_clear).setOnClickListener(v -> tvReceiver.setText(""));
    }

    private void sendAction1() {
        tvReceiver.append("\n发送者发送广播\n");
        Intent intent = new Intent("Action1");
        Bundle bundle = new Bundle();
        bundle.putString(DATA, "发送者的message");
        intent.putExtras(bundle);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    public void initData(Bundle bundle) {
        register();
    }

    private Receiver1 receiver1;
    private Receiver2 receiver2;
    private Receiver3 receiver3;

    private void register() {
        IntentFilter intentFilter1 = buildFilter();
        intentFilter1.setPriority(1);
        receiver1 = new Receiver1();
        registerReceiver(receiver1, intentFilter1);

        IntentFilter intentFilter2 = buildFilter();
        intentFilter2.setPriority(2);
        receiver2 = new Receiver2();
        registerReceiver(receiver2, intentFilter2);

        IntentFilter intentFilter3 = buildFilter();
        intentFilter3.setPriority(3);
        receiver3 = new Receiver3();
        registerReceiver(receiver3, intentFilter3);
    }

    private IntentFilter buildFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Action1");
        return intentFilter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    private void unregister() {
        unregisterReceiver(receiver1);
        unregisterReceiver(receiver2);
        unregisterReceiver(receiver3);
    }

    class Receiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = getResultExtras(true);
            tvReceiver.append(String.format("Receiver1(Priority=1)接受:%s\n", bundle.getString(DATA)));
        }
    }

    class Receiver2 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = getResultExtras(true);
            tvReceiver.append(String.format("Receiver2(Priority=2)接受:%s\n", bundle.getString(DATA)));
            bundle.putString(DATA, "Receiver2修改");
            setResultExtras(bundle);
            if (tbOpen.isChecked()) {
                tvReceiver.append("Receiver2拦截，终止广播\n");
                abortBroadcast();
            }
        }
    }

    class Receiver3 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = getResultExtras(true);
            tvReceiver.append(String.format("Receiver3(Priority=3)接受:%s\n", bundle.getString(DATA)));
            bundle.putString(DATA, "Receiver3修改");
            setResultExtras(bundle);
        }
    }
}
