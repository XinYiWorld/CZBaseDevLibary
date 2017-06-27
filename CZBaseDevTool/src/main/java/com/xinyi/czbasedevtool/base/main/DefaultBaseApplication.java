package com.xinyi.czbasedevtool.base.main;

import org.xutils.my_define.CZDbManager;

/**
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public abstract  class DefaultBaseApplication extends BaseApplication {
    @Override
    public boolean isOnlineEnvironment() {
        return false;
    }

    @Override
    public void initDependentSDK(boolean isOnline) {
        //设置默认的数据库系统
        String dbName = isOnline ? getPackageName() + "_online.db" : getPackageName() + "_test.db";
        String dbDir =  isOnline ? getFilesDir() + "online/db" : getFilesDir() + "test/db";

        new CZDbManager.Builder()
                .application(this)
                .isOnline(isOnline)
                .dbName(dbName)
                .dbDir(dbDir)
                .build();
    }

}
