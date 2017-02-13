package com.xinyi.czbasedevtool.base.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.xinyi.czbasedevtool.base.view.ContentViewHolder;

import java.io.IOException;

/**
 * BaseActivity的默认实现类-第2层
 * author:Created by ChenZhang on 2016/6/22 0022.
 * function:
 */
public  abstract class DefaultBaseFragment extends BaseFragment {
    @Override
    public boolean useDataBinding() {
        return true;
    }


    @Override
    public void annotationBind(@NonNull Activity target, View view) {

    }

    @Override
    public void annotationsUnbind(@NonNull Activity target, View view) {

    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public void bindView(ContentViewHolder contentViewHolder) throws IOException {

    }

    @Override
    public void setState(View v, boolean b) {
        if(v != null)v.setEnabled(b);
    }

    @Override
    public void notifyViewAndDataChangedLinkedWithUser() {

    }

    @Override
    public void requestDataOnCreate() {

    }

    @Override
    public void doOnLazyResume() {

    }

    @Override
    public void getBundleExtras(Bundle extras) {}



}
