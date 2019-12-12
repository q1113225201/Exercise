package com.sjl.exercise.function.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;
import com.sjl.exercise.util.PermisstionUtil;

public class RecordActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_record;
    }

    private TextView tvTime;

    @Override
    public void initView() {
        tvTime = findViewById(R.id.tv_time);
        findViewById(R.id.btn_start).setOnClickListener(v -> startRecord());
        findViewById(R.id.btn_stop).setOnClickListener(v -> stopRecord());
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startRecord() {
        PermisstionUtil.requestPermissions(this, PermisstionUtil.getPermissions(PermisstionUtil.STORAGE, PermisstionUtil.MICROPHONE), 100, "需要录音和文件读写权限", new PermisstionUtil.OnPermissionResult() {
            @Override
            public void granted(int requestCode) {
                Intent intent = new Intent(RecordActivity.this, RecordService.class);
                intent.putExtra("action", "start");
                startService(intent);
            }

            @Override
            public void denied(int requestCode) {

            }
        });
    }

    private void stopRecord() {
        Intent intent = new Intent(RecordActivity.this, RecordService.class);
        intent.putExtra("action", "stop");
        startService(intent);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermisstionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
