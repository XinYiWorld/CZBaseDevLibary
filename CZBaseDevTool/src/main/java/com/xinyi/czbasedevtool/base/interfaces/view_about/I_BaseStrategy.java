package com.xinyi.czbasedevtool.base.interfaces.view_about;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 陈章 on 2016/11/15 0015.
 * func:
 */

public interface I_BaseStrategy {
    boolean useDataBinding() ;          //是否使用DataBinding技术

    //Activity绑定使用
    void annotationBind(@NonNull Activity target,View view);              //注解初始化（如ButterKnife）
    void annotationsUnbind(@NonNull Activity target,View view);           //注销注解

    //Fragment绑定使用
    void annotationBind(Fragment target, View view);                      //注解初始化（如ButterKnife）
    void annotationsUnbind(Fragment target,View view);                    //注销注解
}
