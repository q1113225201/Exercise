package com.sjl.exercise.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BitmapUtil {
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFilePath(String filePath, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//	    InputStream is = new FileInputStream(filePath);
//	    System.out.println("is=" + is);
        BitmapFactory.decodeFile(filePath, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampledBitmapFromFilePath(String filePath, int width) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//	    InputStream is = new FileInputStream(filePath);
//	    System.out.println("is=" + is);
        BitmapFactory.decodeFile(filePath, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = Math.round((float) options.outWidth / (float) width);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 旋转图片
     *
     * @param bitmap            Bitmap
     * @param orientationDegree 旋转角度
     * @return 旋转之后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, int orientationDegree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(orientationDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * 保存bitmap图片
     *
     * @param bitmap
     * @param outFile
     * @return
     * @throws IOException
     */
    public static boolean save(Bitmap bitmap, String outFile)
            throws IOException {
        if (TextUtils.isEmpty(outFile) || bitmap == null)
            return false;
        byte[] data = bitmap2byte(bitmap);
        return save(data, outFile);
    }

    /**
     * 保存图片字节
     *
     * @param bitmapBytes
     * @param outFile
     * @return
     * @throws IOException
     */
    public static boolean save(byte[] bitmapBytes, String outFile)
            throws IOException {
        FileOutputStream output = null;
        FileChannel channel = null;
        try {
            new File(outFile).delete();
            FileUtil.createFile(outFile);
            output = new FileOutputStream(outFile);
            channel = output.getChannel();
            ByteBuffer buffer = ByteBuffer.wrap(bitmapBytes);
            channel.write(buffer);
            return true;
        } finally {
            IOUtil.close(channel);
            IOUtil.close(output);
        }
    }

    /**
     * 将Bitmap转化为字节数组
     *
     * @param bitmap
     * @return byte[]
     * @throws IOException
     */
    public static byte[] bitmap2byte(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] array = baos.toByteArray();
            baos.flush();
            return array;
        } finally {
            IOUtil.close(baos);
        }
    }

    /**
     * 获取压缩图片
     *
     * @param srcPath
     * @param width
     * @param height
     * @return
     */
    private Bitmap compressImage(String srcPath, float width, float height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只获取图片信息
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);

        options.inJustDecodeBounds = false;
        int scaleWidth = (int) (options.outWidth / width);
        int scaleHeight = (int) (options.outHeight / height);
        //获取较大的压缩比
        int scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
        scale = scale <= 0 ? 1 : scale;
        options.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(srcPath, options);
        return compressImage(bitmap);//大小压缩完后进行质量压缩
    }

    /**
     * 质量压缩图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        //图片质量
        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {//如果图片大于100k，进行压缩
            //清空baos
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        //压缩后的数据存放在bais中
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        //生成图片
        Bitmap resultBmp = BitmapFactory.decodeStream(bais, null, null);
        return resultBmp;
    }

    /**
     * Drawable转Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);
            return bitmap;
        }
    }

    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * view转换为bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }
}
