package com.xinyi.czbasedevtool.base.utils.io.concrete;

import com.xinyi.czbasedevtool.base.interfaces.listener.OnTaskDoneListener;
import com.xinyi.czbasedevtool.base.utils.io.FileUtil;
import com.xinyi.czbasedevtool.base.utils.io.interfaces.Abs_StrategyWrite;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * author:Created by ChenZhang on 2016/7/6 0006.
 * function:
 */
public class PrintWriterStrategyWrite implements Abs_StrategyWrite {
    @Override
    public void write(byte[] bytes, File target) {
       throw new RuntimeException("can't write bytes by PrintWriterStrategyWrite,it only support String");
    }


    @Override
    public void write(InputStream in, File target,OnTaskDoneListener... taskDoneListener) {
        throw new RuntimeException("can't write stream by PrintWriterStrategyWrite,it only support String");
    }

    @Override
    public void copy(File in, File target,OnTaskDoneListener... taskDoneListener) {
        throw new RuntimeException("TODO");
    }


    @Override
    public void write(String txt, File target) {
        PrintWriter printer = null;
        try{
            FileUtil.createIfFileNotExist(target.getAbsolutePath());
            printer = new PrintWriter(target);
            printer.print(txt);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(printer != null){
                printer.close();
            }
        }
    }

}
