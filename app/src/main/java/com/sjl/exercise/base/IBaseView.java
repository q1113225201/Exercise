package com.sjl.exercise.base;

import android.os.Bundle;

/**
 * IBaseView
 *
 * @author 林zero
 * @date 2019/3/16
 */
public interface IBaseView {
    int getContentLayout();

    void initView();

    void initData(Bundle bundle);
}
