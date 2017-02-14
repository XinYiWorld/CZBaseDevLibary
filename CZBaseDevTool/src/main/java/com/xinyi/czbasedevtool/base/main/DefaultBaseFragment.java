package com.xinyi.czbasedevtool.base.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.R;
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

    private View findViewById(int id){
        return rootView.findViewById(id);
    }

    @Override
    public final View getLeftWrapperView() {
        return findViewById(R.id.title_layout_left_wrapper);
    }

    @Override
    public View getLeftTxtView() {
        return findViewById(R.id.title_layout_left_txt);
    }

    @Override
    public final TextView getTitleView() {
        return (TextView) findViewById(R.id.title_layout_center_title);
    }

    @Override
    public final View getRightWrapperView() {
        return findViewById(R.id.title_layout_right_wrapper);
    }

    @Override
    public final TextView getRightTxtView() {
        return (TextView) findViewById(R.id.title_layout_right_txt);
    }

    @Override
    public  void initTitleLayout() {
        View leftWrapperView = getLeftWrapperView();
        if(leftWrapperView == null){
            Log.e(TAG, "initTitleLayout: getLeftWrapperView() return null ,if you use the right id in the ids.xml?");
        }else{
            leftWrapperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftWrapperViewClick();
                }
            });
        }

        View rightWrapperView = getRightWrapperView();
        if(rightWrapperView == null){
            Log.e(TAG, "initTitleLayout: getRightWrapperView() return null,if you use the right id in the ids.xml?");
        }else{
            rightWrapperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightWrapperViewClick();
                }
            });
        }

        TextView titleView = getTitleView();
        if(titleView == null){
            Log.e(TAG, "initTitleLayout: getTitleView() return null,if you use the right id in the ids.xml?");
        }else{
            titleView.setText(getTitleString());
        }


        TextView rightTxtView = getRightTxtView();
        if(rightTxtView == null){
            Log.e(TAG, "initTitleLayout: getRightTxtView() return null,if you use the right id in the ids.xml?");
        }else{
            rightTxtView.setText(getRightTextString());
        }

        //you can override the method and do something else here
    }

    @Override
    public void onLeftWrapperViewClick() {
        getActivity().finish();
    }

    @Override
    public void onRightWrapperViewClick() {

    }

    @Override
    public String getTitleString() {
        return "";
    }

    @Override
    public String getRightTextString() {
        return "";
    }

    @Override
    public String getLeftTextString() {
        return "";
    }

}
