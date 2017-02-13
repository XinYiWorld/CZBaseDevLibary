package com.xinyi.czbasedevtool.base.databinding;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.xinyi.czbasedevtool.base.interfaces.I_TwoThread;
import com.xinyi.czbasedevtool.base.interfaces.view_about.I_Navigater;
import com.xinyi.czbasedevtool.base.manager.TwoThreadManager;
import com.xinyi.czbasedevtool.base.utils.ExceptionUtil;

/**
 * author:Created by ChenZhang on 2016/6/24 0024.
 * function:
 */
public abstract  class BaseEventHandler  implements I_TwoThread,I_Navigater{
    protected Context mContext;
    protected ViewDataBinding outBinding;
    private  I_Navigater i_navigater;
    protected Activity attachedActivity;


    public BaseEventHandler(Context mContext) {
        this.mContext = mContext;
        init();
    }

    public BaseEventHandler(Context mContext, ViewDataBinding outBinding) {
        this.mContext = mContext;
        this.outBinding = outBinding;
        init();
    }

    public BaseEventHandler(Context mContext, ViewDataBinding outBinding, I_Navigater i_navigater) {
        this.mContext = mContext;
        this.outBinding = outBinding;
        this.i_navigater = i_navigater;
        init();
    }

    protected  abstract void init();
    public void init(Activity mActivity){
        this.attachedActivity = mActivity;
    }


    @Override
    public void runOnUiThread(Runnable runnable) {
        TwoThreadManager.getInstantce().runOnUiThread(runnable);
    }

    @Override
    public void runOnBackThread(Runnable runnable) {
        TwoThreadManager.getInstantce().runOnBackThread(runnable);
    }

    @Override
    public void postDelay(Runnable runnable, long delayedTimeMillis) {
        TwoThreadManager.getInstantce().postDelay(runnable,delayedTimeMillis);
    }

    @Override
    public void readyGo(Class<?> clazz) {
        ExceptionUtil.throwNullPointerException(i_navigater);
        i_navigater.readyGo(clazz);
    }

    @Override
    public void readyGo(Class<?> clazz, Bundle bundle) {
        ExceptionUtil.throwNullPointerException(i_navigater);
        i_navigater.readyGo(clazz,bundle);
    }

    @Override
    public void readyGoThenKill(Class<?> clazz) {
        ExceptionUtil.throwNullPointerException(i_navigater);
        readyGoThenKill(clazz);
    }

    @Override
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        ExceptionUtil.throwNullPointerException(i_navigater);
        i_navigater.readyGoThenKill(clazz,bundle);
    }

    @Override
    public void readyGoForResult(Class<?> clazz, int requestCode) {
        ExceptionUtil.throwNullPointerException(i_navigater);
        i_navigater.readyGoForResult(clazz,requestCode);
    }

    @Override
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        ExceptionUtil.throwNullPointerException(i_navigater);
        i_navigater.readyGoForResult(clazz,requestCode,bundle);
    }
}
