package com.sjl.exercise.ui.view.conflict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * ConflictViewGroup
 *
 * @author 林zero
 * @date 2019/6/12
 */
public class ConflictHViewGroup extends ViewGroup {
    private static final String TAG = "ConflictHViewGroup";

    public ConflictHViewGroup(Context context) {
        this(context, null);
    }

    public ConflictHViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ConflictHViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private int childWidth = 0;

    private void init() {
        scroller = new Scroller(getContext());
        velocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int measureWidth = 0;
        int measureHeight = 0;
        if (childCount == 0) {
            measureWidth = 0;
            measureHeight = 0;
        } else {
            childWidth = getChildAt(0).getMeasuredWidth();
            if (widthMode == MeasureSpec.AT_MOST) {
                measureWidth = childWidth * childCount;
            } else {
                measureWidth = widthSize;
            }
            if (heightMode == MeasureSpec.AT_MOST) {
                measureHeight = getChildAt(0).getMeasuredHeight();
            } else {
                measureHeight = heightSize;
            }
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent:" + ev.getAction());
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (ev.getX() - lastX);
                int dy = (int) (ev.getY() - lastY);
                if (Math.abs(dx) > Math.abs(dy)) {
                    //横向滑动大于纵向滑动
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        lastX = (int) ev.getX();
        lastY = (int) ev.getY();
        return intercepted;
    }

    private int lastX = -1;
    private int lastY = -1;

    private int currentIndex = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent:" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                scrollBy((int) (lastX - event.getX()), 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    //超过50切换到其他child
                    currentIndex = xVelocity > 0 ? (currentIndex - 1) : (currentIndex + 1);
                } else {
                    //不超过50再滑一半
                    currentIndex = (scrollX + childWidth / 2) / childWidth;
                }
                currentIndex = Math.max(0, Math.min(currentIndex, getChildCount() - 1));
                int dx = currentIndex * childWidth - scrollX;
                smoothScrollBy(dx, 0);
                velocityTracker.clear();
                break;
        }
        lastX = (int) event.getX();
        lastY = (int) event.getY();
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        scroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        velocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
