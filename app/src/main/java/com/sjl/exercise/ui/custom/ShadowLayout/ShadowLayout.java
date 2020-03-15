package com.sjl.exercise.ui.custom.ShadowLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.sjl.exercise.R;

public class ShadowLayout extends FrameLayout {
    private static final String TAG = "ShadowLayout";
    private int mWidth;
    private int mHeight;
    private int mShadowColor;
    private float mShadowLimit;
    private float mCornerRadius;
    private float mLeftRadius;
    private float mRightRadius;
    private float mDx;
    private float mDy;
    private boolean isShowShadow;
    private Paint shadowPaint;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
        shadowPaint = new Paint();
        shadowPaint.setColor(mShadowColor);
        if (isShowShadow) {
            shadowPaint.setShadowLayer(mShadowLimit, mDx, mDy, mShadowColor);
        }
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }
        try {
            //默认是显示
            isShowShadow = attr.getBoolean(R.styleable.ShadowLayout_isShowShadow, true);
            mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_cornerRadius, 0);
            mLeftRadius = attr.getDimension(R.styleable.ShadowLayout_leftRadius, 0);
            mRightRadius = attr.getDimension(R.styleable.ShadowLayout_rightRadius, 0);
            //默认扩散区域宽度
            mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_shadowLimit, 0);
            //x轴偏移量
            mDx = attr.getDimension(R.styleable.ShadowLayout_dx, 0);
            //y轴偏移量
            mDy = attr.getDimension(R.styleable.ShadowLayout_dy, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_shadowColor, Color.GREEN);
        } finally {
            attr.recycle();
        }
        mLeftRadius = mCornerRadius != 0 ? mCornerRadius : mLeftRadius;
        mRightRadius = mCornerRadius != 0 ? mCornerRadius : mRightRadius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽度
        int height = MeasureSpec.getSize(heightMeasureSpec);//获取高度

        int leftRight = (int) (mShadowLimit * 2 + Math.abs(mDx));
        int topBottom = (int) (mShadowLimit * 2 + Math.abs(mDy));
        mWidth = mWidth == 0 ? (width + leftRight) : mWidth;
        mHeight = mHeight == 0 ? (height + topBottom) : mHeight;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawShadow(canvas, getWidth(), getHeight());
    }

    private void drawShadow(Canvas canvas, int width, int height) {
        float realWidth = width - getPaddingLeft() - getPaddingRight() - Math.abs(mDx) - mLeftRadius - mRightRadius - 2 * mShadowLimit;
        float realHeight = height - getPaddingTop() - getPaddingBottom() - Math.abs(mDy) - 2 * mShadowLimit;
        Log.e(TAG,mWidth+","+mHeight+"-"+realWidth+","+realHeight+"-"+mLeftRadius+","+mRightRadius+"-"+mDx+","+mDy+"-"+mShadowLimit);
        Path path = new Path();
        float startX = mShadowLimit + getPaddingLeft() + mLeftRadius;
        float startY = height - getPaddingBottom() - mShadowLimit-Math.abs(mDy);
        path.moveTo(startX, startY);
        if (mLeftRadius != 0) {
            //左侧圆角矩形
            RectF leftRect = new RectF(startX-mLeftRadius, startY - realHeight,
                    startX + mLeftRadius, startY);
            path.arcTo(leftRect, 90, 180);
        } else {
            path.lineTo(startX, startY - realHeight);
        }
        path.lineTo(startX + realWidth, startY - realHeight);
        if (mRightRadius != 0) {
            //右侧圆角矩形
            RectF rightRect = new RectF(startX + realWidth - mRightRadius, startY - realHeight,
                    startX + realWidth + mRightRadius, startY);
            path.arcTo(rightRect, 270, 180);
        } else {
            path.lineTo(startX + realWidth, startY);
        }
        path.close();
        path.offset(mDx, mDy);
        canvas.drawPath(path, shadowPaint);
    }

}