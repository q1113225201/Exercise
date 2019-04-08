package com.sjl.exercise.module.service.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.sjl.aidl.ICustomAidlInterface;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RemoteService extends Service {
    private static final String TAG = "RemoteService";
    ICustomAidlInterface.Stub stub = new ICustomAidlInterface.Stub() {

        @Override
        public String getCurrentTime() throws RemoteException {
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        Toast.makeText(this, "远程服务onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        Toast.makeText(this, "远程服务onBind", Toast.LENGTH_SHORT).show();
        return stub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        Toast.makeText(this, "远程服务onUnbind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        Toast.makeText(this, "远程服务onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
