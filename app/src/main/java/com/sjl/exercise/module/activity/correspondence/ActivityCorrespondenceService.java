package com.sjl.exercise.module.activity.correspondence;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class ActivityCorrespondenceService extends Service {
    public String data;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new CorrespondenceBinder();
    }

    public class CorrespondenceBinder extends Binder {
        public void sendData(String data) {
            ActivityCorrespondenceService.this.data = data;
            Toast.makeText(ActivityCorrespondenceService.this, "data:" + data, Toast.LENGTH_SHORT).show();
        }
    }
}
