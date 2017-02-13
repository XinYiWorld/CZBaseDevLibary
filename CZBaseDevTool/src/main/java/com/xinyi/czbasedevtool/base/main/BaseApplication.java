package com.xinyi.czbasedevtool.base.main;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.view.WindowManager;

import com.xinyi.czbasedevtool.base.interfaces.InterfaceForApplication;

import com.xinyi.czbasedevtool.base.manager.ActivityManager;
import com.xinyi.czbasedevtool.base.manager.CrashHandler;
import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;

/**
 * author:Created by ChenZhang on 2016/6/20 0020.
 * function:
 */
public abstract  class BaseApplication extends MultiDexApplication implements InterfaceForApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        initSystemStaticInstance();
        initDependentSDK(isOnlineEnvironment());
    }

    @Override
    public void initSystemStaticInstance() {
        SystemStaticInstanceManager.setmApplicationContext(this.getApplicationContext());
        SystemStaticInstanceManager.setmWindowManager( (WindowManager) getSystemService(Context.WINDOW_SERVICE));
        SystemStaticInstanceManager.setmResources(getResources());
        SystemStaticInstanceManager.setmConnectivityManager((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE));
        SystemStaticInstanceManager.setmPackageManager(getPackageManager());
        SystemStaticInstanceManager.setCommonHandler(new Handler());
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        doOnDestory();
    }

    @Override
    public void doOnDestory() {
        ActivityManager.getInstance().AppExit(this);

        SystemStaticInstanceManager.getCommonHandler().removeCallbacksAndMessages(null);
    }
}
