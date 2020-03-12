package com.sjl.exercise.ui.custom.ShadowLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.sjl.exercise.R;

public class ShadowLayout extends FrameLayout {

    private int mShadowColor;
    private float mShadowLimit;
    private float mCornerRadius;
    private float mLeftRadius;
    private float mRightRadius;
    private float mDx;
    private float mDy;
    private boolean leftShow;
    private boolean rightShow;
    private boolean topShow;
    private boolean bottomShow;
    private Paint shadowPaint;

    private int leftPading;
    private int topPading;
    private int rightPading;
    private int bottomPading;

    private boolean isShowShadow = true;
    private boolean isSym;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽度
        int height = MeasureSpec.getSize(heightMeasureSpec);//获取高度

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getVisibility() != View.GONE) {
                measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);
            }
        }
        setMeasuredDimension(width + (int) mShadowLimit * 2, height + (int) mShadowLimit * 2);
//        setBackgroundCompat(width + (int) mShadowLimit * 2, height + (int) mShadowLimit * 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            getChildAt(0).post(new Runnable() {
                @Override
                public void run() {
                    setBackgroundCompat(w, h);
                }
            });
        }
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(attrs);
        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);
        setPading();
    }

    private void setPading() {
        //控件区域是否对称，默认是对称。不对称的话，那么控件区域随着阴影区域走
        if (isSym) {
            int xPadding = (int) (mShadowLimit + Math.abs(mDx));
            int yPadding = (int) (mShadowLimit + Math.abs(mDy));

            leftPading = leftShow ? xPadding : 0;
            topPading = topShow ? yPadding : 0;
            rightPading = rightShow ? xPadding : 0;
            bottomPading = bottomShow ? yPadding : 0;
        } else {
            if (Math.abs(mDy) > mShadowLimit) {
                mDy = mDy > 0 ? mShadowLimit : (-mShadowLimit);
            }
            if (Math.abs(mDx) > mShadowLimit) {
                mDx = mDx > 0 ? mShadowLimit : (-mShadowLimit);
            }
            topPading = topShow ? (int) (mShadowLimit - mDy) : 0;
            bottomPading = bottomShow ? (int) (mShadowLimit + mDy) : 0;
            rightPading = rightShow ? (int) (mShadowLimit - mDx) : 0;
            leftPading = leftShow ? (int) (mShadowLimit + mDx) : 0;
        }
        setPadding(leftPading, topPading, rightPading, bottomPading);
    }

    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(int w, int h) {
        if (isShowShadow) {
            //判断传入的颜色值是否有透明度
            isAddAlpha(mShadowColor);
            Bitmap bitmap = createShadowBitmap(w, h, mLeftRadius, mRightRadius,
                    mShadowLimit, mDx, mDy, mShadowColor, Color.TRANSPARENT);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                setBackgroundDrawable(drawable);
            } else {
                setBackground(drawable);
            }
        } else {
            //解决不执行onDraw方法的bug就是给其设置一个透明色
            setBackgroundColor(Color.parseColor("#00000000"));
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
            leftShow = attr.getBoolean(R.styleable.ShadowLayout_leftShow, true);
            rightShow = attr.getBoolean(R.styleable.ShadowLayout_rightShow, true);
            bottomShow = attr.getBoolean(R.styleable.ShadowLayout_bottomShow, true);
            topShow = attr.getBoolean(R.styleable.ShadowLayout_topShow, true);
            mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_cornerRadius, 0);
            mLeftRadius = attr.getDimension(R.styleable.ShadowLayout_leftRadius, 0);
            mRightRadius = attr.getDimension(R.styleable.ShadowLayout_rightRadius, 0);
            //默认扩散区域宽度
            mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_shadowLimit, 5);

            //x轴偏移量
            mDx = attr.getDimension(R.styleable.ShadowLayout_dx, 0);
            //y轴偏移量
            mDy = attr.getDimension(R.styleable.ShadowLayout_dy, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_shadowColor, Color.GREEN);
            isSym = attr.getBoolean(R.styleable.ShadowLayout_isSym, mDx == 0 && mDy == 0);
        } finally {
            attr.recycle();
        }
        mLeftRadius = mCornerRadius != 0 ? mCornerRadius : mLeftRadius;
        mRightRadius = mCornerRadius != 0 ? mCornerRadius : mRightRadius;
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight,
                                      float leftRadius, float rightRadius,
                                      float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {
        //优化阴影bitmap大小,将尺寸缩小至原来的1/4。
        dx = dx / 4;
        dy = dy / 4;
        shadowWidth = shadowWidth / 4;
        shadowHeight = shadowHeight / 4;
        leftRadius = leftRadius / 4;
        rightRadius = rightRadius / 4;
        shadowRadius = shadowRadius / 4;
        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                shadowRadius + leftRadius,
                shadowRadius,
                shadowWidth - shadowRadius - rightRadius,
                shadowHeight - shadowRadius);

        if (isSym) {
            shadowRect.top += Math.abs(dy);
            shadowRect.bottom -= Math.abs(dy);
            shadowRect.left += Math.abs(dx);
            shadowRect.right -= Math.abs(dx);
        } else {
            shadowRect.top -= dy;
            shadowRect.bottom -= dy;
            shadowRect.right -= dx;
            shadowRect.left -= dx;
        }
        shadowPaint.setColor(fillColor);
        if (!isInEditMode()) {//dx  dy
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }
        if (shadowRect.left + shadowRadius < shadowRect.right) {
            canvas.drawRoundRect(shadowRect, 0, 0, shadowPaint);
//            shadowRect.left-=shadowRadius;
//            shadowRect.right+=shadowRadius;
//            canvas.clipRect(shadowRect, Region.Op.INTERSECT);
        }

        RectF leftShadowRect = new RectF(
                shadowRadius,
                shadowRadius,
                (shadowRadius + leftRadius) * 2,
                shadowHeight - shadowRadius);

        if (isSym) {
            leftShadowRect.top += Math.abs(dy);
            leftShadowRect.bottom -= Math.abs(dy);
            leftShadowRect.left += Math.abs(dx);
            leftShadowRect.right -= Math.abs(dx);
        } else {
            leftShadowRect.top -= dy;
            leftShadowRect.bottom -= dy;
            leftShadowRect.right -= dx;
            leftShadowRect.left -= dx;
        }
        if (leftShadowRect.left + shadowRadius < leftShadowRect.right) {
            canvas.drawArc(leftShadowRect, 90, 180, true, shadowPaint);
//            leftShadowRect.right-=shadowRadius;
//            canvas.clipRect(leftShadowRect, Region.Op.INTERSECT);
        }
        RectF rightShadowRect = new RectF(
                shadowWidth - (rightRadius + shadowRadius) * 2,
                shadowRadius,
                shadowWidth - shadowRadius,
                shadowHeight - shadowRadius);
        if (isSym) {
            rightShadowRect.top += Math.abs(dy);
            rightShadowRect.bottom -= Math.abs(dy);
            rightShadowRect.left += Math.abs(dx);
            rightShadowRect.right -= Math.abs(dx);
        } else {
            rightShadowRect.top -= dy;
            rightShadowRect.bottom -= dy;
            rightShadowRect.right -= dx;
            rightShadowRect.left -= dx;
        }
        if (rightShadowRect.left + shadowRadius < rightShadowRect.right) {
            canvas.drawArc(rightShadowRect, 270, 180, true, shadowPaint);
//            rightShadowRect.left+=shadowRadius;
//            canvas.clipRect(rightShadowRect, Region.Op.INTERSECT);
        }
//        canvas.drawPath(calculateAndRenderShadow(getChildAt(0)),shadowPaint);
        return output;
    }

    public void isAddAlpha(int color) {
        //获取单签颜色值的透明度，如果没有设置透明度，默认加上#2a
        if (Color.alpha(color) == 255) {
            String red = Integer.toHexString(Color.red(color));
            String green = Integer.toHexString(Color.green(color));
            String blue = Integer.toHexString(Color.blue(color));
            if (red.length() == 1) {
                red = "0" + red;
            }
            if (green.length() == 1) {
                green = "0" + green;
            }
            if (blue.length() == 1) {
                blue = "0" + blue;
            }
            String endColor = "#99" + red + green + blue;
            mShadowColor = convertToColorInt(endColor);
        }
    }

    private int convertToColorInt(String argb)
            throws IllegalArgumentException {
        if (!argb.startsWith("#")) {
            argb = "#" + argb;
        }
        return Color.parseColor(argb);
    }

}