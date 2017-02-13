package com.xinyi.czbasedevtool.base.interfaces;

/**
 * author:Created by ChenZhang on 2016/6/20 0020.
 * function:
 */
public interface InterfaceForApplication {

     void initSystemStaticInstance();    //初始化系统级别的静态常量
     boolean isOnlineEnvironment();      //是否线上环境
     void initDependentSDK(boolean isOnline);            //初始化第3方依赖sdk
     void doOnDestory();
}
