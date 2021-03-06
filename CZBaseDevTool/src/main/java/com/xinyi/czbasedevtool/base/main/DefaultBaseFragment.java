package com.xinyi.czbasedevtool.base.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.utils.ResourceUtil;
import com.xinyi.czbasedevtool.base.utils.TLog;
import com.xinyi.czbasedevtool.base.view.ContentViewHolder;
import com.xinyi.czbasedevtool.base.view.TitleViewHolder;

import java.io.IOException;

/**
 * BaseActivity的默认实现类-第2层
 * author:Created by ChenZhang on 2016/6/22 0022.
 * function:
 */
public  abstract class DefaultBaseFragment extends BaseFragment {

    protected TitleViewHolder titleViewHolder;

    @Override
    public boolean useDataBinding() {
        return true;
    }


    //注解绑定与解绑
    @Override
    public void annotationBind(@NonNull Activity target, View view) {
        //do nothing here
    }

    @Override
    public void annotationsUnbind(@NonNull Activity target, View view) {
        //do nothing here
    }


    @Override
    public void annotationBind(Fragment target, View view) {

    }

    @Override
    public void annotationsUnbind(Fragment target, View view) {

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
    public void doAfterCreate() {

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

    //标题模板方法
    @Override
    public View getTitleRootView() {
        return findViewById(R.id.title_layout_root);
    }


    @Override
    public final View getLeftWrapperView() {
        return findViewById(R.id.title_layout_left_wrapper);
    }

    @Override
    public TextView getLeftTxtView() {
        return (TextView) findViewById(R.id.title_layout_left_txt);
    }

    @Override
    public ImageView getLeftImageView() {
        return (ImageView) findViewById(R.id.title_layout_left_image);
    }

    @Override
    public final TextView getTitleView() {
        return (TextView) findViewById(R.id.title_layout_center_title);
    }

    @Override
    public View getTitleWrapperView() {
        return findViewById(R.id.title_layout_center_wrapper);
    }

    @Override
    public ImageView getTitleImageView() {
        return (ImageView) findViewById(R.id.title_layout_center_image);
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
    public ImageView getRightImageView() {
        return (ImageView) findViewById(R.id.title_layout_right_image);
    }

    @Override
    public  void initTitleLayout() {
        titleViewHolder = new TitleViewHolder();

        titleViewHolder.titleRootView = getTitleRootView();
        if(titleViewHolder.titleRootView == null){
            TLog.e(TAG, "initTitleLayout: getTitleRootView() return null ,if you use the right id in the ids.xml?");
        }else{

        }

        View leftWrapperView = getLeftWrapperView();
        titleViewHolder.leftWrapperView = leftWrapperView;
        if(leftWrapperView == null){
            TLog.e(TAG, "initTitleLayout: getLeftWrapperView() return null ,if you use the right id in the ids.xml?");
        }else{
            leftWrapperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftWrapperViewClick();
                }
            });
        }

        View rightWrapperView = getRightWrapperView();
        titleViewHolder.rightWrapperView = rightWrapperView;
        if(rightWrapperView == null){
            TLog.e(TAG, "initTitleLayout: getRightWrapperView() return null,if you use the right id in the ids.xml?");
        }else{
            rightWrapperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightWrapperViewClick();
                }
            });
        }

        View titleWrapperView = getTitleWrapperView();
        titleViewHolder.titleWrapperView = titleWrapperView;
        if(titleWrapperView == null){
            TLog.e(TAG, "initTitleLayout: getTitleWrapperView() return null,if you use the right id in the ids.xml?");
        }else{
            titleWrapperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTitleWrapperViewClick();
                }
            });
        }


        TextView titleView = getTitleView();
        titleViewHolder.titleView = titleView;
        if(titleView == null){
            TLog.e(TAG, "initTitleLayout: getTitleView() return null,if you use the right id in the ids.xml?");
        }else{
            titleView.setText(getTitleStringRes() == -1 ? getTitleString() : ResourceUtil.getString(getTitleStringRes()));
            titleView.setVisibility(TextUtils.isEmpty(getTitleString()) && getTitleStringRes() == -1 ? View.GONE : View.VISIBLE);
        }


        TextView rightTxtView = getRightTxtView();
        titleViewHolder.rightTxtView = rightTxtView;
        if(rightTxtView == null){
            TLog.e(TAG, "initTitleLayout: getRightTxtView() return null,if you use the right id in the ids.xml?");
        }else{
            rightTxtView.setText(getRightTextStringRes() == -1 ?  getRightTextString() : ResourceUtil.getString(getRightTextStringRes()));
            rightTxtView.setVisibility(TextUtils.isEmpty(getRightTextString()) && getRightTextStringRes() == -1 ? View.GONE : View.VISIBLE);
        }

        TextView leftTxtView = getLeftTxtView();
        titleViewHolder.leftTxtView = leftTxtView;
        if(leftTxtView == null){
            TLog.e(TAG, "initTitleLayout: getLeftTxtView() return null,if you use the right id in the ids.xml?");
        }else{
            leftTxtView.setText(getLeftTextStringRes() == -1 ? getLeftTextString() : ResourceUtil.getString(getLeftTextStringRes()));
            leftTxtView.setVisibility(TextUtils.isEmpty(getLeftTextString()) && getLeftTextStringRes() == -1 ? View.GONE : View.VISIBLE);
        }


        ImageView leftImageView = getLeftImageView();
        titleViewHolder.leftImageView = leftImageView;
        if(leftImageView == null){
            TLog.e(TAG, "initTitleLayout: getLeftImageView() return null,if you use the right id in the ids.xml?");
        }else{
            int leftImageResId = getLeftImageResId();
            if(leftImageResId != -1){
                leftImageView.setImageResource(leftImageResId);
                leftImageView.setVisibility(View.VISIBLE);
            }else{
                leftImageView.setVisibility(View.GONE);
            }
        }

        ImageView rightImageView = getRightImageView();
        titleViewHolder.rightImageView = rightImageView;
        if(rightImageView == null){
            TLog.e(TAG, "initTitleLayout: getRightImageView() return null,if you use the right id in the ids.xml?");
        }else{
            int rightImageResId = getRightImageResId();
            if(rightImageResId != -1){
                rightImageView.setImageResource(rightImageResId);
                rightImageView.setVisibility(View.VISIBLE);
            }else{
                rightImageView.setVisibility(View.GONE);
            }
        }

        ImageView titleImageView = getTitleImageView();
        titleViewHolder.titleImageView = titleImageView;
        if(titleImageView == null){
            TLog.e(TAG, "initTitleLayout: getTitleImageView() return null,if you use the right id in the ids.xml?");
        }else{
            int titleImageResId = getTitleImageResId();
            if(titleImageResId != -1){
                titleImageView.setImageResource(titleImageResId);
                titleImageView.setVisibility(View.VISIBLE);
            }else{
                titleImageView.setVisibility(View.GONE);
            }
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
    public void onTitleWrapperViewClick() {

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

    @Override
    public int getTitleStringRes() {
        return -1;
    }

    @Override
    public int getRightTextStringRes() {
        return -1;
    }

    @Override
    public int getLeftTextStringRes() {
        return -1;
    }

    @Override
    public int getLeftImageResId() {
        return -1;
    }

    @Override
    public int getRightImageResId() {
        return -1;
    }

    @Override
    public int getTitleImageResId() {
        return -1;
    }
}
