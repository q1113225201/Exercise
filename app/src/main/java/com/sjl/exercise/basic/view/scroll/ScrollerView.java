package com.sjl.exercise.basic.view.scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * ScrollerView
 *
 * @author 林zero
 * @date 2019/5/29
 */
public class ScrollerView extends View {
    private static final String TAG = "ScrollerView";

    public ScrollerView(Context context) {
        this(context, null);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;

    private void init() {
        paint = new Paint();
        paint.setTextSize(32f);
        paint.setColor(Color.WHITE);
    }

    private int startX;
    private int startY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                smoothScrollTo(startX- (int)event.getX(), startY- (int) event.getY());
                break;
        }
        return true;
    }

    private Scroller scroller = new Scroller(getContext());

    /**
     * 滚动到指定位置
     */
    private void smoothScrollTo(int destX, int destY) {
        Log.e(TAG, "FinalX=" + scroller.getFinalX() + ",FinalY=" + scroller.getFinalY());
        Log.e(TAG, "destX=" + destX + ",destY=" + destY);
        scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), destX, destY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            Log.e(TAG, "CurrX=" + scroller.getCurrX() + ",CurrY=" + scroller.getCurrY());
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("text", getWidth() / 2, getHeight()/2, paint);
    }
}
