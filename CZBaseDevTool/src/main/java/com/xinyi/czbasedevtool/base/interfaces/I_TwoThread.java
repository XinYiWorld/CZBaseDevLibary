package com.xinyi.czbasedevtool.base.interfaces;

/**
 * author:Created by ChenZhang on 2016/6/24 0024.
 * function:
 */
public interface I_TwoThread {
    void runOnUiThread(Runnable runnable);
    void runOnBackThread(Runnable runnable);
    void postDelay(Runnable runnable,long delayedTimeMillis);
}
