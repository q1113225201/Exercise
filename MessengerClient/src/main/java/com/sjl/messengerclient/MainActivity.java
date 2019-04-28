package com.sjl.messengerclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                String message = msg.getData().getString("message");
                Log.i(TAG, "客户端收到服务端返回消息：" + message);
                tvResult.setText("客户端收到服务端返回消息：\n" + message);
            }
        }
    });
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务端Messenger
            serviceMessenger = new Messenger(service);
            Toast.makeText(MainActivity.this,"服务端连接成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceMessenger = null;
        }
    };
    private Messenger serviceMessenger;

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_bind).setOnClickListener(v -> {
            Intent intent = new Intent();
            //AndroidManifest.xml中定义的服务器端Action
            intent.setAction("com.sjl.messenger");
            //5.0后无法通过隐式Intent绑定远程Service，需设置包名
            intent.setPackage("com.sjl.exercise");
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        });
        findViewById(R.id.btn_send).setOnClickListener(v -> {
            Message message = Message.obtain();
            message.what = 1;
            Bundle bundle = new Bundle();
            bundle.putString("message", String.format("客户端%s发送信息", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
            message.setData(bundle);
            //设置回复处理的Messenger
            message.replyTo = clientMessenger;
            if (serviceMessenger != null) {
                try {
                    serviceMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }
}
