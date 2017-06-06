package com.xinyi.czbasedevtool.base.interfaces.net_about;

import android.view.View;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResponseBean;

/**
 * 网络请求结果处理
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public interface I_HttpResultHandler{
    <T> void onSuccess(int requestCode, BaseHttpResponseBean codeInfoBean,T successBean);
    void onFailure(int requestCode,Throwable e);
    void setState(View v, boolean b);               //设置发起http的view是否可以点击
}
