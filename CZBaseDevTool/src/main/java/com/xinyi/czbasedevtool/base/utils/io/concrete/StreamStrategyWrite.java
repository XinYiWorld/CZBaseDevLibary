package com.xinyi.czbasedevtool.base.utils.io.concrete;

import com.xinyi.czbasedevtool.base.interfaces.listener.OnTaskDoneListener;
import com.xinyi.czbasedevtool.base.utils.io.FileUtil;
import com.xinyi.czbasedevtool.base.utils.io.interfaces.Abs_StrategyWrite;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Abs_StrategyWrite的具体策略类：流形式写文件策略类
 * author:Created by ChenZhang on 2016/7/6 0006.
 * function:
 */
public class StreamStrategyWrite implements Abs_StrategyWrite{

    @Override
    public void write(byte[] bytes, File target) {
        BufferedOutputStream bufOut = null;
        try{
            FileUtil.createIfFileNotExist(target.getAbsolutePath());
            bufOut = new BufferedOutputStream(new FileOutputStream(target,FileUtil.if_append));
            bufOut.write(bytes);
            bufOut.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufOut != null){
                try{
                    bufOut.close();
                }catch(Exception  e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void write(String txt, File target) {
       write(txt.getBytes(), target);
    }

    @Override
    public void write(InputStream in, File target,OnTaskDoneListener... taskDoneListener) {
        FileUtil.createIfFileNotExist(target.getAbsolutePath());
        BufferedOutputStream bufOut = null;
        try{
            FileUtil.createIfFileNotExist(target.getAbsolutePath());
            bufOut = new BufferedOutputStream(new FileOutputStream(target,FileUtil.if_append));
            BufferedInputStream bis = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bufOut.write(buffer, 0, len);
                bufOut.flush();
            }
            bufOut.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufOut != null){
                try{
                    bufOut.close();
                }catch(Exception  e){
                    e.printStackTrace();
                }
            }

            if(in != null){
                try{
                    in.close();
                }catch(Exception  e){
                    e.printStackTrace();
                }
            }
            if(taskDoneListener != null && taskDoneListener.length > 0){
                taskDoneListener[0].done(null);
            }
        }

    }

    @Override
    public void copy(File in, File target,OnTaskDoneListener... taskDoneListener) {
        if(!in.exists()){
            FileUtil.createIfFileNotExist(in.getAbsolutePath());
        }

        if(!target.exists()){
            FileUtil.createIfFileNotExist(target.getAbsolutePath());
        }

        try{
             write(new BufferedInputStream(new FileInputStream(in)),target,taskDoneListener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
