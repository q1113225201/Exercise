package com.sjl.exercise.basic.view.conflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * ConflictViewGroup
 *
 * @author 沈建林
 * @date 2019/6/12
 */
public class ConflictHViewGroup extends ViewGroup {
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
            if (widthMode == MeasureSpec.AT_MOST) {
                measureWidth = getChildAt(0).getMeasuredWidth() * childCount;
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
                int childWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    private int lastX;
    private int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = true;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        return intercepted;
    }
}
