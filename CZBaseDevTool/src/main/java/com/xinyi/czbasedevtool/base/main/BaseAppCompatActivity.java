package com.xinyi.czbasedevtool.base.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.xinyi.czbasedevtool.base.bean.UploadFileWrapper;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_HttpResultHandler;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_Try_RequestServer;
import com.xinyi.czbasedevtool.base.interfaces.view_about.BaseView;
import com.xinyi.czbasedevtool.base.interfaces.view_about.I_BaseStrategy;
import com.xinyi.czbasedevtool.base.interfaces.view_about.I_Navigater;
import com.xinyi.czbasedevtool.base.interfaces.view_about.ProgressView;
import com.xinyi.czbasedevtool.base.interfaces.view_about.TitleView;
import com.xinyi.czbasedevtool.base.manager.ActivityManager;
import com.xinyi.czbasedevtool.base.manager.net_about.HttpMaster;
import com.xinyi.czbasedevtool.base.manager.ui_about.DialogMaster;
import com.xinyi.czbasedevtool.base.manager.ui_about.NavigateMaster;
import com.xinyi.czbasedevtool.base.view.ContentViewHolder;

import java.util.List;

/**
 * BaseActivity抽象类-第1层
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public   abstract class BaseAppCompatActivity<BindingObj> extends AppCompatActivity implements I_BaseStrategy,TitleView,BaseView,ProgressView,I_HttpResultHandler,I_Try_RequestServer,I_Navigater{
    protected  final String TAG = this.getClass().getName();
    protected   View mWindowDecorView = null;

    private ViewDataBinding viewDataBinding;
    private BindingObj parentBinding;
    private boolean isOnResumeExecuted = false;
    private HttpMaster httpMaster;

    protected Context mContext = null;
    private NavigateMaster navigateMaster;
    protected FragmentManager mSupportFragmentManager;
    protected DialogMaster dialogMaster;
    private View rootView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mSupportFragmentManager = getSupportFragmentManager();


        Log.i(TAG, "onCreate: ");
        try{
            requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏系统自带的标题栏目;

            ActivityManager.getInstance().addActivity(this);

            doOnCreateInit();
            //---------------------------状态栏变色----------------------------------------------------------
            initStatusBar();
            //-------------------------------------------------------------------------------------

            fillAndBindView();

            initTitleLayout();

            //分层 合成聚合原则
            httpMaster = new HttpMaster(this, this,this);
            dialogMaster = new DialogMaster(this);
            navigateMaster = new NavigateMaster(this);
            mWindowDecorView = this.getWindow().getDecorView();     //注意要在setContenView之后调用

            //取参数
            Bundle extras = getIntent().getExtras();
            if (null != extras) {
                getBundleExtras(extras);
            }

            requestDataOnCreate();
            isOnResumeExecuted = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            if(getStatusBarTintResource() != -1){
                tintManager.setStatusBarTintResource(getStatusBarTintResource());//通知栏所需颜色
            }
        }
    }


    private void fillAndBindView() throws java.io.IOException {
        if(useDataBinding()){
            //两种填充View方式
            if(getLayoutID() > 0){
                viewDataBinding = DataBindingUtil.setContentView(this, getLayoutID());
                getWindow().setBackgroundDrawable(null);    //UI优化，不给DecroView添加背景
            }else{
                viewDataBinding = DataBindingUtil.bind(getContentView());
            }
            if(viewDataBinding != null){
                parentBinding = ((BindingObj)viewDataBinding);
                annotationBind(this,null);
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
            setContentView(rootView);
            getWindow().setBackgroundDrawable(null);    //UI优化，不给DecroView添加背景

            annotationBind(this,null);
            bindView(new ContentViewHolder(rootView));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        notifyViewAndDataChangedLinkedWithUser();
        if(!isOnResumeExecuted){
            doOnLazyResume();
        }
        isOnResumeExecuted = true;
    }

    @Override
    public void finish() {
        super.finish();
        Log.i(TAG, "finish: ");
        ActivityManager.getInstance().removeActivity(this);
    }


    /**
     * 从一个activity返回之前activity会走这个方法。
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }


    //打印生命周期，便于调试。
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        annotationsUnbind(this,null);
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    //状态栏设置
    protected int getStatusBarTintResource(){
        return -1;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    //dialogMaster层 负责dialog的显示
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
