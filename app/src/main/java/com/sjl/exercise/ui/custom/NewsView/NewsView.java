package com.sjl.exercise.ui.custom.NewsView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjl.exercise.R;

import java.util.List;

/**
 * NewsView
 *
 * @author 林zero
 * @date 2019/4/15
 */
public class NewsView extends NestedScrollView {
    private static final int SCROLL = 100;

    public NewsView(@NonNull Context context) {
        this(context, null);
    }

    public NewsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private LinearLayout mDataLayout;
    private int pageSize = 3;
    private int interval = 1000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL:
                    int scrollY = getScrollY();
                    if (scrollY >= maxHeight) {
                        scrollTo(0, 0);
                        smoothScrollBy(0, itemHeight);
                    } else {
                        smoothScrollBy(0, itemHeight);
                    }
                    postDelayed(() -> sendEmptyMessage(SCROLL), interval);
                    break;
            }
        }
    };

    private void init(Context context) {
        mDataLayout = new LinearLayout(getContext());
        mDataLayout.setOrientation(LinearLayout.VERTICAL);
        mDataLayout.setLayoutParams(new NestedScrollView.LayoutParams(-1, -1));
        addView(mDataLayout);
        initAttr(context);
    }

    private void initAttr(Context context) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NewsView);

    }

    public void setData(List<String> list, int pageSize) {
        this.pageSize = pageSize;
        if (list.size() == 0 || pageSize == 0) {
            mDataLayout.removeAllViews();
            return;
        }
        if (list.size() >= pageSize) {
            list.addAll(list.subList(0, pageSize));
        }
        for (String item : list) {
            View view = View.inflate(getContext(), R.layout.item_news, null);
            ((TextView) view.findViewById(R.id.tv_content)).setText(item);
            mDataLayout.addView(view);
        }
        post(() -> {
            itemHeight = -1;
            for (int i = 0; i < mDataLayout.getChildCount(); i++) {
                itemHeight = Math.max(itemHeight, mDataLayout.getChildAt(i).getMeasuredHeight());
            }
            maxHeight = itemHeight * (mDataLayout.getChildCount() - pageSize);
            LayoutParams layoutParams = (LayoutParams) mDataLayout.getLayoutParams();
            layoutParams.height = itemHeight * pageSize;
            mDataLayout.setLayoutParams(layoutParams);
        });
        postDelayed(() -> {
            if (handler != null) {
                handler.sendEmptyMessage(SCROLL);
            }
        }, interval);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), itemHeight * pageSize);
    }

    private int maxHeight = -1;
    private int itemHeight = 30;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
