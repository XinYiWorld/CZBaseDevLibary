package com.xinyi.czbasedevtool.base.interfaces.view_about;

import android.view.View;
import android.widget.TextView;

/**
 * 标题处理接口
 * author:Created by ZhangChen on 2016/7/21 0021.
 * detail:
 */
public interface TitleView {
    //find view
    View getLeftWrapperView();
    View getLeftTxtView();
    View getLeftImageView();
    TextView getTitleView();
    View getRightWrapperView();
    View getRightTxtView();
    View getRightImageView();
    void initTitleLayout();

    //bind event
    void onLeftWrapperViewClick();
    void onRightWrapperViewClick();

    //init title
    String getTitleString();
    String getLeftTextString();
    String getRightTextString();

    int getTitleStringRes();
    int getLeftTextStringRes();
    int getRightTextStringRes();

    //init image
    int getLeftImageResId();
    int getRightImageResId();
}
