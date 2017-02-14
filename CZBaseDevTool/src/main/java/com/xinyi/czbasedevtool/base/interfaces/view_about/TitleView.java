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
    TextView getTitleView();
    View getRightWrapperView();
    View getRightTxtView();
    void initTitleLayout();

    //bind event
    void onLeftWrapperViewClick();
    void onRightWrapperViewClick();

    //init title
    String getTitleString();
    String getLeftTextString();
    String getRightTextString();
}
