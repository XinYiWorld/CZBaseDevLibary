package com.xinyi.czbasedevtool.base.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.view.WindowManager;

import com.xinyi.czbasedevtool.base.utils.ExceptionUtil;

/**
 * author:Created by ChenZhang on 2016/6/20 0020.
 * function:
 */
public class SystemStaticInstanceManager {
    private static WindowManager mWindowManager = null;
    private static ConnectivityManager mConnectivityManager = null;
    private static Resources mResources = null;
    private static Context mApplicationContext = null;
    private static PackageManager mPackageManager = null;
    private static Handler  commonHandler = null;

    public static Handler getCommonHandler() {
        return commonHandler;
    }

    public static void setCommonHandler(Handler commonHandler) {
        SystemStaticInstanceManager.commonHandler = commonHandler;
    }

    public static PackageManager getmPackageManager() {
        return mPackageManager;
    }

    public static void setmPackageManager(PackageManager mPackageManager) {
        SystemStaticInstanceManager.mPackageManager = mPackageManager;
    }

    public static Context getmApplicationContext() {
        return mApplicationContext;
    }

    public static void setmApplicationContext(Context mApplicationContext) {
        SystemStaticInstanceManager.mApplicationContext = mApplicationContext;
    }

    public static ConnectivityManager getmConnectivityManager() {
        ExceptionUtil.throwNullPointerException(mConnectivityManager);
        return mConnectivityManager;
    }

    public static void setmConnectivityManager(ConnectivityManager mConnectivityManager) {
        SystemStaticInstanceManager.mConnectivityManager = mConnectivityManager;
    }

    public static WindowManager getmWindowManager() {
        ExceptionUtil.throwNullPointerException(mWindowManager);
        return mWindowManager;
    }

    public static void setmWindowManager(WindowManager mWindowManager) {
        SystemStaticInstanceManager.mWindowManager = mWindowManager;
    }

    public static Resources getmResources() {
        ExceptionUtil.throwNullPointerException(mResources);
        return mResources;
    }

    public static void setmResources(Resources mResources) {
        SystemStaticInstanceManager.mResources = mResources;
    }
}
