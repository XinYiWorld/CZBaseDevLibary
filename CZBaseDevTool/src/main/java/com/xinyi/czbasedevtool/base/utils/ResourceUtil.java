package com.xinyi.czbasedevtool.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ArrayRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;

import com.xinyi.czbasedevtool.base.R;
import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;

import java.util.Arrays;
import java.util.List;

/**
 * author:Created by ChenZhang on 2016/6/27 0027.
 * function:
 */
public class ResourceUtil {
    public static  float getDimension(@DimenRes int id){
        return SystemStaticInstanceManager.getmResources().getDimension(id);
    }

    public static List<String> getStringList(@ArrayRes int id){
        return Arrays.asList(SystemStaticInstanceManager.getmResources().getStringArray(id));
    }

    public static int getColor(int colorResId){
        return SystemStaticInstanceManager.getmResources().getColor(colorResId);
    }


    public static String getString(@StringRes int id,Object ...obj){
        if(obj != null){
            return SystemStaticInstanceManager.getmApplicationContext().getString(id,obj);
        }else{
            return SystemStaticInstanceManager.getmApplicationContext().getString(id);
        }
    }

    public static String getString(Context context, @StringRes int id, Object ...obj){
        if(obj != null){
            return context.getString(id,obj);
        }else{
            return context.getString(id);
        }
    }

}
