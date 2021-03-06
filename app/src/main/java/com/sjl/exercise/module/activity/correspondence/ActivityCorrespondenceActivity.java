package com.sjl.exercise.module.activity.correspondence;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class ActivityCorrespondenceActivity extends BaseActivity {
    private static final String TAG = "ActivityCorrespondenceA";

    @Override
    public int getContentLayout() {
        return R.layout.activity_activity_correspondence;
    }

    private int cnt = 0;

    @Override
    public void initView() {
        findViewById(R.id.btn_bind).setOnClickListener(v -> bindService());
        findViewById(R.id.btn_send).setOnClickListener(v -> {
            if (correspondenceBinder != null) {
                correspondenceBinder.sendData(String.valueOf(cnt++));
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "服务绑定，Activity发送消息");
            correspondenceBinder = (ActivityCorrespondenceService.CorrespondenceBinder) service;
            correspondenceBinder.sendData("服务绑定，Activity发送消息");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "服务解绑：" + name);
            Toast.makeText(ActivityCorrespondenceActivity.this, "服务解绑：" + name, Toast.LENGTH_SHORT).show();
            serviceConnection = null;
            correspondenceBinder = null;
        }
    };
    private ActivityCorrespondenceService.CorrespondenceBinder correspondenceBinder;

    /**
     * 绑定服务通信
     */
    private void bindService() {
        bindService(new Intent(this, ActivityCorrespondenceService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }

}
