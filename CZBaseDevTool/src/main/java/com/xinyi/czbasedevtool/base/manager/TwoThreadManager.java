package com.xinyi.czbasedevtool.base.manager;

import android.util.Log;

import com.xinyi.czbasedevtool.base.interfaces.I_TwoThread;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * UI线程和子线程执行方法    使用RxJava来实现
 * author:Created by ChenZhang on 2016/6/24 0024.
 * function:
 */
public class TwoThreadManager implements I_TwoThread{
    private final static TwoThreadManager instantce = new TwoThreadManager();

    private TwoThreadManager(){}

    public static TwoThreadManager getInstantce(){
        return instantce;
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        Observable.just(runnable)
                .subscribeOn(AndroidSchedulers.mainThread()); // 指定 subscribe() 发生在 ui  线程
    }

    @Override
    public void runOnBackThread(Runnable runnable) {
        Observable.just(runnable)
                .subscribeOn(Schedulers.io()); // 指定 subscribe() 发生在 IO 线程
    }

    //延时操作
    @Override
    public void postDelay(final Runnable runnable, long delayedTimeMillis) {
        Observable.timer(delayedTimeMillis,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        runnable.run();
                    }
                });



    }



}
