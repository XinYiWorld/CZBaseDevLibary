package com.xinyi.czbasedevtool.base.manager.net_about;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xinyi.czbasedevtool.base.bean.BaseHttpResultBean;
import com.xinyi.czbasedevtool.base.bean.UploadFileWrapper;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_HttpResultHandler;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_Real_RequestServer;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_Try_RequestServer;
import com.xinyi.czbasedevtool.base.interfaces.view_about.ProgressView;
import com.xinyi.czbasedevtool.base.manager.ui_about.dialog.SystemDialogFactory;
import com.xinyi.czbasedevtool.base.utils.ExceptionUtil;
import com.xinyi.czbasedevtool.base.utils.JSONUtil;
import com.xinyi.czbasedevtool.base.utils.NetUtil;
import com.xinyi.czbasedevtool.base.utils.TLog;
import com.xinyi.czbasedevtool.base.utils.ToastUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 网络请求Main类
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public class HttpMaster implements I_Try_RequestServer, I_Real_RequestServer {
    private String TAG = "HttpMaster";
    private Context mContext;
    private I_HttpResultHandler httpResultHandler;
    private ProgressView progressView;
    private View mWindowDecorView;


    public HttpMaster(Context mContext, I_HttpResultHandler httpResultHandler, ProgressView progressView) {
        this.mContext = mContext;
        this.httpResultHandler = httpResultHandler;
        this.progressView = progressView;                   //progress传入null，代表不需要UI显示网络请求的状态。
        TAG = mContext.getClass().getName();
        if (mContext instanceof Activity)
            mWindowDecorView = ((Activity) mContext).getWindow().getDecorView();
    }

    @Override
    public <T, K> void requestData(View executor, int requestCode, Class<T> serviceClass, String methodName, final Class<K> convertedClass, Object[] params, final boolean... isTargetBeanAsList) {
        checkNetStateAndDecideIfGoNext(executor, requestCode, serviceClass, methodName, convertedClass, params, true, isTargetBeanAsList);
    }

    @Override
    public <T> void requestDataWithOutConvert(View executor, int requestCode, Class<T> serviceClass, String methodName, Object[] params) {
        checkNetStateAndDecideIfGoNext(executor, requestCode, serviceClass, methodName, null, params, false, null);
    }

    @Override
    public <T, K> void realRequestData(View executor, int requestCode, Class<T> serviceClass, String methodName, final Class<K> convertedClass, Object[] params, final boolean... isTargetBeanAsList) {
        if (progressView != null) {
            progressView.onShowProgressDialog();
        }
        Method serviceExecuteMethod = reflectServiceMethod(serviceClass, methodName);

        //如果方法名不存在抛出异常
        ExceptionUtil.throwNullPointerException(serviceExecuteMethod, new ExceptionUtil.ExceptionHappenListener() {
            @Override
            public void doNext() {
                hideProgressView();
            }
        });

        try {
            executeService(executor, requestCode, serviceClass, convertedClass, params, serviceExecuteMethod, isTargetBeanAsList);
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressView();
        }
    }

    private void hideProgressView() {
        if (progressView != null) {
            progressView.onHideProgressDialog();
            progressView.reset();
        }
    }

    @Override
    public <T> void realRequestDataWithOutConvert(View executor, int requestCode, Class<T> serviceClass, String methodName, Object[] params) {
        if (progressView != null) {
            progressView.onShowProgressDialog();
        }
        Method serviceExecuteMethod = reflectServiceMethod(serviceClass, methodName);
        //如果方法名不存在抛出异常
        ExceptionUtil.throwNullPointerException(serviceExecuteMethod, new ExceptionUtil.ExceptionHappenListener() {
            @Override
            public void doNext() {
                hideProgressView();
            }
        });

        try {
            executeServiceWithoutConvert(executor, requestCode, serviceClass, params, serviceExecuteMethod);
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressView();
        }
    }

    @Override
    public <T> void uploadFiles(View executor, int requestCode, Class<T> serviceClass, String methodName, List<UploadFileWrapper> uploadFileWrappers) {
        checkNetStateAndDecideIfGoNext(executor, requestCode, serviceClass, methodName, uploadFileWrappers);
    }

    @Override
    public <T> void realUploadFiles(View executor, int requestCode, Class<T> serviceClass, String methodName, List<UploadFileWrapper> uploadFileWrappers) {
        if (progressView != null) {
            progressView.onShowProgressDialog();
        }
        Method serviceExecuteMethod = reflectServiceMethod(serviceClass, methodName);
        //如果方法名不存在抛出异常
        ExceptionUtil.throwNullPointerException(serviceExecuteMethod, new ExceptionUtil.ExceptionHappenListener() {
            @Override
            public void doNext() {
                hideProgressView();
            }
        });

        try {
            executeService(executor, requestCode, serviceClass, serviceExecuteMethod, uploadFileWrappers);
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressView();
        }
    }

    @Override
    public <T> void uploadOneFile(View executor, int requestCode, Class<T> serviceClass, String methodName, UploadFileWrapper uploadFileWrapper) {
        checkNetStateAndDecideIfGoNext(executor, requestCode, serviceClass, methodName, uploadFileWrapper);
    }


    @Override
    public <T> void realUploadOneFile(View executor, int requestCode, Class<T> serviceClass, String methodName, UploadFileWrapper uploadFileWrapper) {
        if (progressView != null) {
            progressView.onShowProgressDialog();
        }
        Method serviceExecuteMethod = reflectServiceMethod(serviceClass, methodName);
        //如果方法名不存在抛出异常
        ExceptionUtil.throwNullPointerException(serviceExecuteMethod, new ExceptionUtil.ExceptionHappenListener() {
            @Override
            public void doNext() {
                hideProgressView();
            }
        });

        List<UploadFileWrapper> parts = new ArrayList<>();
            parts.add(uploadFileWrapper);
        try {
            executeService(executor, requestCode, serviceClass, serviceExecuteMethod, parts);
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressView();
        }
    }

    @Override
    public void downloadFile(View executor, int requestCode, String fileUrl) {
        checkNetStateAndDecideIfGoNext(executor, requestCode, fileUrl);
    }


    @Override
    public void realDownloadFile(View executor, int requestCode, String fileUrl) {
        if (progressView != null) {
            progressView.onShowProgressDialog();
        }
        Observable<ResponseBody> responseBodyObservable = RetrofitClient.getService(DownloadService.class).download(fileUrl);
        responseBodyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(executor, requestCode));

    }


    private <T> void checkNetStateAndDecideIfGoNext(final View executor, final int requestCode, final Class<T> serviceClass, final String methodName, final List<UploadFileWrapper> uploadFileWrappers) {
        //网络环境判断
        if (!NetUtil.isConnected(mContext)) {
            ToastUtil.snakeShort(mWindowDecorView, "请开启网络!");
        } else if (!NetUtil.isWifi(mContext)) {
            SystemDialogFactory.getConfirmDialog(mContext, "您确认在非Wifi状态下请求数据吗？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realUploadFiles(executor, requestCode, serviceClass, methodName, uploadFileWrappers);
                }
            }).show();
        } else {
            realUploadFiles(executor, requestCode, serviceClass, methodName, uploadFileWrappers);
        }
    }

    private <T> void checkNetStateAndDecideIfGoNext(final View executor, final int requestCode, final Class<T> serviceClass, final String methodName, final UploadFileWrapper uploadFileWrapper) {
        //网络环境判断
        if (!NetUtil.isConnected(mContext)) {
            ToastUtil.snakeShort(mWindowDecorView, "请开启网络!");
        } else if (!NetUtil.isWifi(mContext)) {
            SystemDialogFactory.getConfirmDialog(mContext, "您确认在非Wifi状态下请求数据吗？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realUploadOneFile(executor, requestCode, serviceClass, methodName, uploadFileWrapper);
                }
            }).show();
        } else {
            realUploadOneFile(executor, requestCode, serviceClass, methodName, uploadFileWrapper);
        }
    }

    private <T, K> void checkNetStateAndDecideIfGoNext(final View executor, final int requestCode, final Class<T> serviceClass, final String methodName, final Class<K> convertedClass, final Object[] params, final boolean needConvert, final boolean... isTargetBeanAsList) {
        //网络环境判断
        if (!NetUtil.isConnected(mContext)) {
            ToastUtil.snakeShort(mWindowDecorView, "请开启网络!");
        } else if (!NetUtil.isWifi(mContext)) {
            SystemDialogFactory.getConfirmDialog(mContext, "您确认在非Wifi状态下请求数据吗？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (needConvert) {
                        realRequestData(executor, requestCode, serviceClass, methodName, convertedClass, params, isTargetBeanAsList);
                    } else {
                        realRequestDataWithOutConvert(executor, requestCode, serviceClass, methodName, params);
                    }

                }
            }).show();
        } else {
            if (needConvert) {
                realRequestData(executor, requestCode, serviceClass, methodName, convertedClass, params, isTargetBeanAsList);
            } else {
                realRequestDataWithOutConvert(executor, requestCode, serviceClass, methodName, params);
            }
        }
    }


    private void checkNetStateAndDecideIfGoNext(final View executor, final int requestCode, final String fileUrl) {
        //网络环境判断
        if (!NetUtil.isConnected(mContext)) {
            ToastUtil.snakeShort(mWindowDecorView, "请开启网络!");
        } else if (!NetUtil.isWifi(mContext)) {
            SystemDialogFactory.getConfirmDialog(mContext, "您确认在非Wifi状态下请求数据吗？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    realDownloadFile(executor, requestCode, fileUrl);
                }
            }).show();
        } else {
            realDownloadFile(executor, requestCode, fileUrl);
        }
    }

    //Retrofit的请求结果
    @NonNull
    private TagRxObserver<Object> getObserver(final View executor, final int requestCode) {

        return new TagRxObserver<Object>(executor, requestCode) {
            @Override
            public void onCompleted() {
                //TODO
                TLog.i(TAG, "getObserver onCompleted");
                httpResultHandler.setState(executor, true);
                hideProgressView();
            }

            @Override
            public void onError(Throwable e) {
                //TODO
                TLog.i(TAG, "getObserver onError");
                httpResultHandler.setState(executor, true);
                hideProgressView();
                ToastUtil.snakeShort(mWindowDecorView, "解析数据异常!");
                e.printStackTrace();
            }

            @Override
            public void onNext(Object targetBean) {
                TLog.i(TAG, "getObserver onNext");
                httpResultHandler.setState(executor, true);
                hideProgressView();
                if (targetBean != null) {
                    httpResultHandler.onSuccess(getRequestCode(), targetBean);
                }
            }
        };
    }

    //服务器返回的json可能需要进一步转换
    @NonNull
    private <K> Func1<BaseHttpResultBean, Object> getFunc(final Class<K> convertedClass, final boolean[] isTargetBeanAsList) {
        return new Func1<BaseHttpResultBean, Object>() {             //Result-List<BannerListSQL>
            @Override
            public Object call(BaseHttpResultBean result) {
                String dataJson = result.getData();
                TLog.i(TAG, "BaseAppCompatActivity解析的json串：" + dataJson);

                //如果返回的结果不合法，则程序不往后进行。
                if (!result.OK()) {
                    ToastUtil.snakeShort(mWindowDecorView, "请求数据失败！");
                    return null;
                }

                //isTargetBeanAsList 判断json是按照List解析还是单个的bean来解析
                if (isTargetBeanAsList != null && isTargetBeanAsList[0]) {
                    return JSONUtil.getArray(dataJson, convertedClass);
                } else {
                    return JSON.parseObject(dataJson, convertedClass);
                }
            }
        };
    }

    //反射出Service里的方法
    @Nullable
    private <T> Method reflectServiceMethod(Class<T> serviceClass, String methodName) {
        Method serviceExecuteMethod = null;
        Method[] declaredMethods = serviceClass.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            if (declaredMethods[i].getName().equals(methodName)) {
                serviceExecuteMethod = declaredMethods[i];
                break;
            }
        }
        return serviceExecuteMethod;
    }

    private <T> void executeServiceWithoutConvert(final View executor, int requestCode, Class<T> serviceClass, Object[] params, Method serviceExecuteMethod) throws IllegalAccessException, InvocationTargetException {
        httpResultHandler.setState(executor, false);
        if (params != null) {     //有参
            ((Observable<BaseHttpResultBean>) (serviceExecuteMethod.invoke(RetrofitClient.getService(serviceClass), params)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver(executor, requestCode));
        } else {                  //无参
            ((Observable<BaseHttpResultBean>) (serviceExecuteMethod.invoke(RetrofitClient.getService(serviceClass))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver(executor, requestCode));
        }
    }

    private <T, K> void executeService(final View executor, int requestCode, Class<T> serviceClass, Class<K> convertedClass, Object[] params, Method serviceExecuteMethod, boolean[] isTargetBeanAsList) throws IllegalAccessException, InvocationTargetException {
        httpResultHandler.setState(executor, false);
        if (params != null) {         //有参
            ((Observable<BaseHttpResultBean>) (serviceExecuteMethod.invoke(RetrofitClient.getService(serviceClass), params)))
                    .map(getFunc(convertedClass, isTargetBeanAsList))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver(executor, requestCode));
        } else {                      //无参
            ((Observable<BaseHttpResultBean>) (serviceExecuteMethod.invoke(RetrofitClient.getService(serviceClass))))
                    .map(getFunc(convertedClass, isTargetBeanAsList))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver(executor, requestCode));
        }
    }

    private <T> void executeService(final View executor, int requestCode, Class<T> serviceClass, Method serviceExecuteMethod, List<UploadFileWrapper> parts) throws IllegalAccessException, InvocationTargetException {
        httpResultHandler.setState(executor, false);
        ((Observable<BaseHttpResultBean>) (serviceExecuteMethod.invoke(RetrofitClient.getService(serviceClass), MultipartGenerator.generate(parts))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(executor, requestCode));
    }


}
