package com.xinyi.czbasedevtool.base.interfaces.view_about;

import android.os.Bundle;
import android.view.View;

import com.xinyi.czbasedevtool.base.view.ContentViewHolder;

import java.io.IOException;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public interface BaseView {
    int getLayoutID();      //优先于getContentView
    View getContentView();      //如果getLayoutID无返回值，就采用此填充view，一般用于动态填充view。
    <BindingObj> void bindView(BindingObj binding) throws IOException;
    void bindView(ContentViewHolder contentViewHolder) throws IOException;                            //传统findViewById
    void doOnCreateInit();      //onCreate方法的最前面执行，比findViewById早.
    void doAfterCreate();       //onCreate方法的后面执行
    void requestDataOnCreate();
    void getBundleExtras(Bundle extras);
    void doOnLazyResume();
    void notifyViewAndDataChangedLinkedWithUser();     //刷新view，登录前后。
}
