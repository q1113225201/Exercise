package com.sjl.exercise.module.service.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sjl.aidl.ICustomAidlInterface;
import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

public class RemoteServiceActivity extends BaseActivity {
    private static final String TAG = "RemoteServiceActivity";

    @Override
    public int getContentLayout() {
        return R.layout.activity_remote_service;
    }

    private ICustomAidlInterface iCustomAidlInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCustomAidlInterface = ICustomAidlInterface.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected");
            Toast.makeText(RemoteServiceActivity.this, "service connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            iCustomAidlInterface = null;
        }
    };
    TextView tvResult;

    @Override
    public void initView() {
        tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_bind).setOnClickListener(v -> {
            Intent intent = new Intent();
            //AndroidManifest.xml中定义的服务器端Action
            intent.setAction("com.sjl.aidl.remote");
            //5.0后无法通过隐式Intent绑定远程Service，需设置包名
            intent.setPackage("com.sjl.exercise");
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        });
        findViewById(R.id.btn_time).setOnClickListener(v -> {
            if (iCustomAidlInterface != null) {
                try {
                    tvResult.setText(String.format("客户端调用结果：%s", iCustomAidlInterface.getCurrentTime()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iCustomAidlInterface != null) {
            unbindService(serviceConnection);
        }
    }
}
