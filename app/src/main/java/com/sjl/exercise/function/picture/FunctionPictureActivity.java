package com.sjl.exercise.function.picture;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;
import com.sjl.exercise.util.BitmapUtil;
import com.sjl.exercise.util.PermisstionUtil;

public class FunctionPictureActivity extends BaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_function_picture;
    }

    ImageView ivImage;
    Button btnCamera;
    Button btnAlbum;
    ToggleButton tbCrop;
    TextView tvPath;

    PictureManager pictureManager;

    @Override
    public void initView() {
        ivImage = findViewById(R.id.iv_image);
        btnCamera = findViewById(R.id.btn_camera);
        btnAlbum = findViewById(R.id.btn_album);
        tbCrop = findViewById(R.id.tb_crop);
        tvPath = findViewById(R.id.tv_path);
        btnCamera.setOnClickListener(v -> goCamera());
        btnAlbum.setOnClickListener(v -> goAlbum());
    }

    private void goCamera() {
        PermisstionUtil.requestPermissions(this, PermisstionUtil.getPermissions(PermisstionUtil.STORAGE, PermisstionUtil.CAMERA), 200,
                "拍照需要相机权限和文件读写权限", new PermisstionUtil.OnPermissionResult() {
                    @Override
                    public void granted(int requestCode) {
                        pictureManager.chooseFromCamera(tbCrop.isChecked());
                    }

                    @Override
                    public void denied(int requestCode) {
                        Toast.makeText(FunctionPictureActivity.this, "相机或文件读写权限被禁止，请手动开启。", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goAlbum() {
        PermisstionUtil.requestPermissions(this, PermisstionUtil.STORAGE, 200,
                "拍照需要相机权限和文件读写权限", new PermisstionUtil.OnPermissionResult() {
                    @Override
                    public void granted(int requestCode) {
                        pictureManager.chooseFromAlbum(tbCrop.isChecked());
                    }

                    @Override
                    public void denied(int requestCode) {
                        Toast.makeText(FunctionPictureActivity.this, "文件读写权限被禁止，请手动开启。", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void initData(Bundle bundle) {
        pictureManager = new PictureManager(this, new PictureManager.PictureCallback() {
            @Override
            public void onFailure(int resultCode) {

            }

            @Override
            public void onSuccess(String outFile) {
                tvPath.setText(outFile);
                ivImage.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFilePath(outFile,300));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermisstionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pictureManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        pictureManager.clearCache();
        super.onDestroy();
    }
}
