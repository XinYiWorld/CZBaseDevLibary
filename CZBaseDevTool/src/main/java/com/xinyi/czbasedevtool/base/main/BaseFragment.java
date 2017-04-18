package com.xinyi.czbasedevtool.base.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinyi.czbasedevtool.base.bean.UploadFileWrapper;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_HttpResultHandler;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_Try_RequestServer;
import com.xinyi.czbasedevtool.base.interfaces.view_about.BaseView;
import com.xinyi.czbasedevtool.base.interfaces.view_about.I_BaseStrategy;
import com.xinyi.czbasedevtool.base.interfaces.view_about.I_Navigater;
import com.xinyi.czbasedevtool.base.interfaces.view_about.ProgressView;
import com.xinyi.czbasedevtool.base.interfaces.view_about.TitleView;
import com.xinyi.czbasedevtool.base.manager.net_about.HttpMaster;
import com.xinyi.czbasedevtool.base.manager.ui_about.DialogMaster;
import com.xinyi.czbasedevtool.base.manager.ui_about.NavigateMaster;
import com.xinyi.czbasedevtool.base.utils.TLog;
import com.xinyi.czbasedevtool.base.view.ContentViewHolder;

import java.util.List;

/**
 * BaseActivity抽象类-第1层
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public   abstract class BaseFragment<BindingObj> extends Fragment implements I_BaseStrategy,TitleView,BaseView,ProgressView,I_HttpResultHandler,I_Try_RequestServer,I_Navigater{
    protected  final String TAG = this.getClass().getName();
    protected   View mWindowDecorView = null;

    private ViewDataBinding viewDataBinding;
    private BindingObj parentBinding;
    private boolean isOnResumeExecuted = false;
    private HttpMaster httpMaster;
    protected DialogMaster dialogMaster;


    protected Context mContext = null;
    private NavigateMaster navigateMaster;
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();

        //分层 合成聚合原则
        httpMaster = new HttpMaster(mContext, this,this);
        dialogMaster = new DialogMaster(mContext,this);
        navigateMaster = new NavigateMaster(mContext);


        TLog.i(TAG, "onCreateView: ");
        try{
            doOnCreateInit();

            fillAndBindView(inflater);

            initTitleLayout();

            mWindowDecorView = getActivity().getWindow().getDecorView();

            //取参数
            Bundle extras = getArguments();
            if (null != extras) {
                getBundleExtras(extras);
            }

            isOnResumeExecuted = false;
            if(useDataBinding()){
                return viewDataBinding.getRoot();
            }else{
                return rootView;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void fillAndBindView(LayoutInflater inflater) throws java.io.IOException {
        if(useDataBinding()){
            if(getLayoutID() > 0){
                viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutID(), null, false);
            }else{
                viewDataBinding =DataBindingUtil.bind(getContentView());
            }
            rootView = viewDataBinding.getRoot();
            annotationBind(this,viewDataBinding.getRoot());
            if(viewDataBinding != null){
                parentBinding = ((BindingObj)viewDataBinding);
                //bind view 兼容传统方式
                bindView(parentBinding);
                bindView(new ContentViewHolder(viewDataBinding.getRoot()));
            }
        }else{
            //两种填充View方式
            if(getLayoutID() > 0){
                rootView = LayoutInflater.from(mContext).inflate(getLayoutID(), null);
            }else{
                rootView = getContentView();
            }
            annotationBind(this,rootView);
            bindView(new ContentViewHolder(rootView));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TLog.i(TAG, "onViewCreated: ");
        requestDataOnCreate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            notifyViewAndDataChangedLinkedWithUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TLog.i(TAG, "onResume: ");
        notifyViewAndDataChangedLinkedWithUser();
        if(!isOnResumeExecuted){
            doOnLazyResume();
        }
        isOnResumeExecuted = true;
    }

    //打印生命周期，便于调试
    public BaseFragment() {
        super();
        TLog.i(TAG, "构造: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        TLog.i(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        TLog.i(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        TLog.i(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        annotationsUnbind(this,null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TLog.i(TAG, "onDestroy: ");
    }


    //dialogMaster 层 负责Dialog的显示
    @Override
    public void onShowProgressDialog() {
        dialogMaster.onShowProgressDialog();
    }

    @Override
    public void onHideProgressDialog() {
        dialogMaster.onHideProgressDialog();
    }

    @Override
    public void onShowMessageDiaolg(@NonNull String msg) {
        dialogMaster.onShowMessageDiaolg(msg);
    }

    @Override
    public void customProgressMsg(int msgRes) {
        dialogMaster.customProgressMsg(msgRes);
    }

    @Override
    public void customProgressMsg(String msg) {
        dialogMaster.customProgressMsg(msg);
    }

    @Override
    public void reset() {
        dialogMaster.reset();
    }

    //httpMaster 负责数据请求层
    @Override
    public <T, K> void requestData(View executor,int requestCode,Class<T> serviceClass, String methodName, Class<K> convertedClass, Object[] params, boolean... isTargetBeanAsList) {
        httpMaster.requestData(executor,requestCode,serviceClass,methodName,convertedClass,params,isTargetBeanAsList);
    }

    @Override
    public <T> void requestDataWithOutConvert(View executor,int requestCode,Class<T> serviceClass, String methodName, Object[] params) {
        httpMaster.requestDataWithOutConvert(executor,requestCode,serviceClass, methodName, params);
    }

    @Override
    public <T> void uploadFiles(View executor,int requestCode,Class<T> serviceClass, String methodName, List<UploadFileWrapper> uploadFileWrappers) {
        httpMaster.uploadFiles(executor,requestCode,serviceClass, methodName, uploadFileWrappers);
    }

    @Override
    public <T> void uploadOneFile(View executor,int requestCode,Class<T> serviceClass, String methodName, UploadFileWrapper uploadFileWrapper) {
        httpMaster.uploadOneFile(executor,requestCode,serviceClass, methodName, uploadFileWrapper);
    }


    @Override
    public void downloadFile(View executor, int requestCode, String fileUrl) {
        httpMaster.downloadFile(executor, requestCode, fileUrl);
    }

    //绑定结果处理器
    @Override
    public void bindHttpResultHandler(I_HttpResultHandler i_httpResultHandler) {
        httpMaster.bindHttpResultHandler(i_httpResultHandler);
    }

    //navigateMaster 负责页面跳转层
    @Override
    public void readyGo(Class<?> clazz) {
        navigateMaster.readyGo(clazz);
    }

    @Override
    public void readyGo(Class<?> clazz, Bundle bundle) {
        navigateMaster.readyGo(clazz, bundle);
    }

    @Override
    public void readyGoThenKill(Class<?> clazz) {
        navigateMaster.readyGoThenKill(clazz);
    }

    @Override
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        navigateMaster.readyGoThenKill(clazz, bundle);
    }

    @Override
    public void readyGoForResult(Class<?> clazz, int requestCode) {
        navigateMaster.readyGoForResult(clazz, requestCode);
    }

    @Override
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        navigateMaster.readyGoForResult(clazz, requestCode, bundle);
    }
}
