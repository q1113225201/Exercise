package com.sjl.exercise.process.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String message = (String) msg.getData().get("message");
                Log.i(TAG, "服务端收到消息：" + message);
                Message replyMessage = Message.obtain();
                replyMessage.what = 2;
                Bundle bundle = new Bundle();
                bundle.putString("message","服务端已收到客户端消息，内容如下---"+message);
                replyMessage.setData(bundle);
                try {
                    msg.replyTo.send(replyMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
