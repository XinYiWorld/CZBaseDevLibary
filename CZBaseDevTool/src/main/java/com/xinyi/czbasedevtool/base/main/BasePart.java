package com.xinyi.czbasedevtool.base.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * 所有模块类的基类  对于布局比较复杂的模块
 * @author Administrator
 *
 * @param <T>
 */
public abstract class BasePart<T> {
    protected FragmentActivity mParentActivity;
    protected Fragment mParentFragment;

    public BasePart(FragmentActivity mParentActivity) {
        this.mParentActivity = mParentActivity;
    }

    public BasePart(Fragment mParentFragment) {
        this.mParentFragment = mParentFragment;
    }

    /**
     * 获取当前模块的View对象
     * @return
     */
    public abstract View getView();

    /**
     * 处理逻辑和数据
     * @param t
     */
    public abstract void setData(T t);

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
}