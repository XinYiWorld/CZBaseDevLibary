package com.xinyi.czbasedevtool.base.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;

/**
 * author:Created by ChenZhang on 2016/6/24 0024.
 * function:
 */
public class ViewUtil {
    public static View inflate(int resId){
        return LayoutInflater.from(SystemStaticInstanceManager.getmApplicationContext()).inflate(resId,null,false);
    }

    public static View inflate(Context context, int resId){
        return LayoutInflater.from(context).inflate(resId,null,false);
    }

    public static <T> T $(View view,int id){
        return (T) view.findViewById(id);
    }
}
