package com.xinyi.czbasedevtool.base.interfaces.view_about;

import android.support.annotation.NonNull;

/**
 * 对话框UI接口
 * author:Created by ZhangChen on 2016/7/27 0027.
 * detail:
 */
public interface ProgressView {
    void onShowProgressDialog();
    void onHideProgressDialog();
    void onShowMessageDiaolg(@NonNull String msg);
    void customProgressMsg(int msgRes);                 //定制进度提示信息
    void customProgressMsg(String msg);
    void reset();
}
