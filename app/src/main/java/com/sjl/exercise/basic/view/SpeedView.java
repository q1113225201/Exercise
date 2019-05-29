package com.sjl.exercise.basic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

/**
 * SpeedView
 *
 * @author 林zero
 * @date 2019/5/28
 */
public class SpeedView extends View {
    private static final String TAG = "SpeedView";

    public SpeedView(Context context) {
        this(context, null);
    }

    public SpeedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;

    private void init() {
        paint = new Paint();
        paint.setTextSize(32f);
        paint.setColor(Color.WHITE);
    }

    private VelocityTracker velocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                text = String.format("xV=%f,yV=%f", velocityTracker.getXVelocity(), velocityTracker.getYVelocity());
                if (velocityTracker.getXVelocity() > 100) {
                    setTranslationX(100);
                } else {
                    setTranslationX(-100);
                }
                Log.e(TAG,text);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.computeCurrentVelocity(500);
                break;
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        velocityTracker.clear();
        velocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    private String text = "";

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawText(text, getWidth() / 2, getHeight()/2, paint);
    }
}
