package com.xinyi.czbasedevtool.base.manager.net_about;

import android.view.View;

import rx.Observer;

/**
 * 带有标识的Observer，便于对url进行统一管理。
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public abstract  class TagRxObserver<T> implements Observer {
    private View executer;
    private int requestCode;

    public TagRxObserver() {
    }

    public TagRxObserver(int requestCode) {
        this.requestCode = requestCode;
    }

    public TagRxObserver(View executer, int requestCode) {
        this.executer = executer;
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public View getExecuter() {
        return executer;
    }

    public void setExecuter(View executer) {
        this.executer = executer;
    }

}
