package com.xinyi.czbasedevtool.base.main;

/**
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public abstract  class DefaultBaseApplication extends BaseApplication {
    @Override
    public boolean isOnlineEnvironment() {
        return false;
    }
}
