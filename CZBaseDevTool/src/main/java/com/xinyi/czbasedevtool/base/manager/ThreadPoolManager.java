package com.xinyi.czbasedevtool.base.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author:Created by ChenZhang on 2016/6/14 0014.
 * function:
 */
public class ThreadPoolManager {
    private static ThreadPoolManager mInstance = new ThreadPoolManager();

    private int corePoolSize ;//核心线程池的大小，就是能够同时执行的线程数量。
    private int maximumPoolSize; //最大线程池的大小，包括了corePoolSize。（如果缓冲队列没有上限，最线程池及其存活时间和单元都将无效。）
    private int keepAliveTime = 1;  //超出workqueue的等待任务的线程的存活时间，就是maximumPoolSize里的等待任务的存活时间。
    private TimeUnit unit = TimeUnit.HOURS;  //时间单位为小时

    private ThreadPoolExecutor executor;

    private ThreadPoolManager(){
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;//当前设备的可用处理器核心数*2 + 1
        maximumPoolSize = corePoolSize;  //由于缓存队列没有上限，所以随便赋值。
        executor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<Runnable>(),  //缓冲队列（不添加容量，表示可以添加无限等待的任务。）
                Executors.defaultThreadFactory(),       //创建线程的工厂
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolManager getInstance(){
        return mInstance;
    }

    //从线程池中执行任务
    public void execute(Runnable task){
        if(task != null){
            executor.execute(task);
        }
    }

    //从线程池中移除任务
    public void remove(Runnable task){
        if(task != null){
            executor.remove(task);
        }
    }
}
