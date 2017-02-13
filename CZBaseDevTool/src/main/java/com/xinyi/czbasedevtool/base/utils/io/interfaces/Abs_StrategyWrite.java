package com.xinyi.czbasedevtool.base.utils.io.interfaces;

import com.xinyi.czbasedevtool.base.interfaces.listener.OnTaskDoneListener;

import java.io.File;
import java.io.InputStream;

/**
 * 写操作抽象策略类
 * author:Created by ChenZhang on 2016/7/6 0006.
 * function:
 */
public interface Abs_StrategyWrite {
    //Strategy
    int STREAM_WRITE = 0;       //OutputStream写文件
    int PRINT_WRITE = 1;        //PrintWriter写文件

    void write(byte[] bytes,File target);
    void write(String txt,File target);
    void write(InputStream in,File target,OnTaskDoneListener... taskDoneListener);
    void copy(File in,File target,OnTaskDoneListener... taskDoneListener);        //也就是复制文件
}
