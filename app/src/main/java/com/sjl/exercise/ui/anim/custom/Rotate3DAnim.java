package com.sjl.exercise.ui.anim.custom;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Rotate3DAnim
 * 自定义动画需重写{@link Animation#initialize}和{@link Animation#applyTransformation(float, Transformation)}
 *
 * @author 林zero
 * @date 2019/6/19
 */
public class Rotate3DAnim extends Animation {
    private float fromDegrees;
    private float toDegrees;
    private float centerX;
    private float centerY;
    private float depthZ;
    private boolean reverse;
    private Camera camera;

    public Rotate3DAnim(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
        this.fromDegrees = fromDegrees;
        this.toDegrees = toDegrees;
        this.centerX = centerX;
        this.centerY = centerY;
        this.depthZ = depthZ;
        this.reverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = fromDegrees + (toDegrees - fromDegrees) * interpolatedTime;

        Matrix matrix = t.getMatrix();
        camera.save();
        if (reverse) {
            camera.translate(0, 0, depthZ * interpolatedTime);
        } else {
            camera.translate(0, 0, depthZ * (1 - interpolatedTime));
        }

        camera.rotateY(degrees);//绕Y轴旋转
//        camera.rotateX(degrees);//绕X轴旋转
//        camera.rotateZ(degrees);//绕Z轴旋转
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);
    }
}
