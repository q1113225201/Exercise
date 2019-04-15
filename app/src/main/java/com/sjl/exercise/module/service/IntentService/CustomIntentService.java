package com.sjl.exercise.module.service.IntentService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * CustomIntentService
 *
 * @author 林zero
 * @date 2019/4/15
 */
public class CustomIntentService extends IntentService {
    private static final String TAG = "CustomIntentService";

    public static final String DATA = "data";
    public static final String ACTION1 = "action1";
    public static final String ACTION2 = "action2";

    public CustomIntentService() {
        super("CustomIntentService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "startId="+startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
                case ACTION1:
                    Log.d(TAG, intent.getStringExtra(DATA));
                    break;
                case ACTION2:
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, intent.getStringExtra(DATA));
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
