package com.xinyi.czbasedevtool.base.interfaces.view_about;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by 陈章 on 2016/11/15 0015.
 * func:
 */

public interface I_BaseStrategy {
    boolean useDataBinding() ;          //是否使用DataBinding技术
    void annotationBind(@NonNull Activity target,View view);              //注解初始化（如ButterKnife）
    void annotationsUnbind(@NonNull Activity target,View view);           //注销注解
}
