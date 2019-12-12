package com.sjl.exercise.function.record;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.sjl.exercise.util.FileUtil;

import java.io.IOException;

public class RecordService extends Service {
    private static final String TAG = "RecordService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, intent.getStringExtra("action"));
        if ("start".equalsIgnoreCase(intent.getStringExtra("action"))) {
            startRecord();
        } else if ("stop".equalsIgnoreCase(intent.getStringExtra("action"))) {
            stopRecord();
        }
        return START_STICKY;
    }

    private MediaRecorder mRecorder = null;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/exercise/";

    private void startRecord() {
        if (mRecorder == null) {
            try {
                FileUtil.makeDirs(path);
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mRecorder.setOutputFile(path + System.currentTimeMillis() + ".m4a");
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                mRecorder.setAudioChannels(1);
                mRecorder.setAudioSamplingRate(44100);
                mRecorder.setAudioEncodingBitRate(192000);

                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRecord() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
