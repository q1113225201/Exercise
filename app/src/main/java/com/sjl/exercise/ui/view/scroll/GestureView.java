package com.sjl.exercise.ui.view.scroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * GestureView
 *
 * @author 林zero
 * @date 2019/5/29
 */
public class GestureView extends View {
    private static final String TAG = "GestureView";

    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        gestureDetector.setOnDoubleTapListener(onDoubleTapListener);
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            //按下，手指触屏就触发
            Log.e(TAG, "onDown:" + e.getAction());
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            //按压，手指按下并且未松开未拖动
            Log.e(TAG, "onShowPress:" + e.getAction());

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //单击，手指触屏后松开
            Log.e(TAG, "onSingleTapUp:" + e.getAction());
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //滚动，手指按下并拖动
            Log.e(TAG, "onScroll: e1=" + e1.getAction() + ",e2=" + e2.getAction() + ",distanceX=" + distanceX + ",distanceY=" + distanceY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            //长按，手指长时间按着屏幕
            Log.e(TAG, "onLongPress:" + e.getAction());
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //快速滑动，手指按下快速滑动后松开
            Log.e(TAG, "onFling: e1=" + e1.getAction() + ",e2=" + e2.getAction() + ",velocityX=" + velocityX + ",velocityY=" + velocityY);
            return false;
        }
    };
    private GestureDetector.OnDoubleTapListener onDoubleTapListener = new GestureDetector.OnDoubleTapListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //单击确认，单击事件后发生，且发生后不再触发双击
            Log.e(TAG, "onSingleTapConfirmed:" + e.getAction());
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //双击，连续两次单击
            Log.e(TAG, "onDoubleTap:" + e.getAction());
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            //双击事件，双击期间手指点击移动松开都会触发
            Log.e(TAG, "onDoubleTapEvent:" + e.getAction());
            return false;
        }
    };
    private GestureDetector gestureDetector = new GestureDetector(getContext(), onGestureListener);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

}
