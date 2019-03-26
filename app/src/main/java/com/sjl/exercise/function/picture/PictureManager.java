package com.sjl.exercise.function.picture;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.sjl.exercise.util.FileUtil;
import com.sjl.exercise.util.UriUtil;

import java.io.File;

public class PictureManager {
    public static final int CAMERA = 1024;
    public static final int ALBUM = 1025;
    private static final int CROP = 1026;
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache/";

    private Context mContext;
    private Activity mActivity;
    private Fragment mFragment;
    private PictureCallback mPictureCallback;

    private boolean mCrop;
    private String tmpFilename;
    private String cropFilename;

    public PictureManager(@NonNull Activity activity, PictureCallback pictureCallback) {
        this.mActivity = activity;
        this.mPictureCallback = pictureCallback;
        init();
    }

    public PictureManager(@NonNull Fragment fragment, PictureCallback pictureCallback) {
        this.mFragment = fragment;
        this.mPictureCallback = pictureCallback;
        init();
    }

    private void init() {
        mContext = mActivity != null ? mActivity : mFragment.getActivity();
    }

    /**
     * 相机选图
     * @param crop
     */
    public void chooseFromCamera(boolean crop) {
        this.mCrop = crop;
        tmpFilename = System.currentTimeMillis() + ".png";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(path + tmpFilename);
        Uri uri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMERA);
    }

    /**
     * 相册选图
     * @param crop
     */
    public void chooseFromAlbum(boolean crop) {
        this.mCrop = crop;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK || (requestCode != CAMERA && requestCode != ALBUM && requestCode != CROP)) {
            if (mPictureCallback != null) {
                mPictureCallback.onFailure(resultCode);
            }
            return;
        }
        if (requestCode == CAMERA) {
            Uri uri = Uri.fromFile(new File(path + tmpFilename));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                uri = getImageContentUri(uri);
            }
            if (mCrop) {
                startPhotoZoom(uri);
            } else {
                onSuccess(UriUtil.uriToFile(mContext, uri));
            }
        } else if (requestCode == ALBUM) {
            if (mCrop) {
                startPhotoZoom(data.getData());
            } else {
                onSuccess(UriUtil.uriToFile(mContext, data.getData()));
            }
        } else if (requestCode == CROP) {
            //不回传数据
            new File(path + tmpFilename).delete();
            onSuccess(path + cropFilename);
        }
    }

    private void onSuccess(String outFile) {
        if (mPictureCallback != null) {
            mPictureCallback.onSuccess(outFile);
        }
    }

    private void startActivityForResult(Intent intent, int code) {
        if (mActivity == null) {
            mFragment.startActivityForResult(intent, code);
        } else {
            mActivity.startActivityForResult(intent, code);
        }
    }

    /**
     * 裁剪
     */
    private void startPhotoZoom(Uri uri) {
        cropFilename = System.currentTimeMillis() + ".png";
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        //自由比例
        /*intent.putExtra("aspectX", 0.1);
        intent.putExtra("aspectY", 0.1);*/
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + cropFilename)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("outputFormat",
        // Bitmap.CompressFormat.PNG.toString());
        // 去黑边
        intent.putExtra("scale", true);
        // intent.putExtra("scaleUpIfNeeded", true);
        // 关闭人脸识别
        // intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP);
    }

    //宽高的比例
    private int aspectX = 1;
    private int aspectY = 1;
    //裁剪图片宽高
    private int outputX = 300;
    private int outputY = 300;

    public void setCropParams(int aspectX, int aspectY, int outputX, int outputY) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.outputX = outputX;
        this.outputY = outputY;
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    private Uri getImageContentUri(Uri uri) {
        File imageFile = new File(uri.getPath());
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return mContext.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 清除path
     */
    public void clearCache() {
        FileUtil.deleteFile(path);
    }

    public interface PictureCallback {
        void onFailure(int resultCode);

        void onSuccess(String outFile);
    }
}
