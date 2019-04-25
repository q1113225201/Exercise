package com.sjl.aidlclient;

import android.app.Activity;
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

import com.sjl.exercise.bean.UserBean;
import com.sjl.exercise.ICustomAidlInterface;

import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private ICustomAidlInterface iCustomAidlInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCustomAidlInterface = ICustomAidlInterface.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected");
            Toast.makeText(MainActivity.this, "service connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            iCustomAidlInterface = null;
        }
    };
    TextView tvTime;
    TextView tvUser;
    int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvTime = findViewById(R.id.tv_time);
        tvUser = findViewById(R.id.tv_user);
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
                    tvTime.setText(String.format("客户端调用time：%s", iCustomAidlInterface.getCurrentTime()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_insert).setOnClickListener(v -> {
            if (iCustomAidlInterface != null) {
                cnt++;
                UserBean userBean = new UserBean();
                userBean.setName("name" + cnt);
                userBean.setAddress("address" + cnt);
                userBean.setAge(cnt);
                try {
                    iCustomAidlInterface.insertUser(userBean);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_get).setOnClickListener(v -> {
            if (iCustomAidlInterface != null) {
                try {
                    List<UserBean> list = iCustomAidlInterface.getUsers();
                    tvUser.setText(list.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_clear).setOnClickListener(v -> {
            if (iCustomAidlInterface != null) {
                try {
                    iCustomAidlInterface.clearUser();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iCustomAidlInterface != null) {
            unbindService(serviceConnection);
        }
    }
}
