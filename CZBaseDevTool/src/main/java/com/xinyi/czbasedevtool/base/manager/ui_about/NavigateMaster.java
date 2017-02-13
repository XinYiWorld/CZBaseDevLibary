package com.xinyi.czbasedevtool.base.manager.ui_about;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xinyi.czbasedevtool.base.interfaces.view_about.I_Navigater;

/**
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public class NavigateMaster implements I_Navigater {
    private Activity mActivity;

    public NavigateMaster(Context mActivity) {
        if (mActivity instanceof  Activity){
            this.mActivity = (Activity)mActivity;
        }else{
            throw new RuntimeException("You must transfer a Activity for NavigateMaster's constructor");
        }
    }

    @Override
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivity(intent);
    }

    @Override
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
    }

    @Override
    public void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivity(intent);
        ((Activity)mActivity).finish();
    }

    @Override
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
        ((Activity)mActivity).finish();
    }

    @Override
    public void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        mActivity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        mActivity.startActivityForResult(intent, requestCode);
    }
}
