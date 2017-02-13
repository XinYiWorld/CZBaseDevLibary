package com.xinyi.czbasedevtool.base.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;

/**
 * 动态的生成shape
 * author:Created by ChenZhang on 2016/6/29 0029.
 * function:
 */
public class DynamicShapeUtil {

    //how to use  -->   view.setBackgroundDrawable
    public static Drawable getRoundCornerDrawable(int fillColor,int roundRadius){
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);  //内部填充颜色
        gd.setCornerRadius(roundRadius);
        return  gd;
    }

    public static Drawable getRoundCornerDrawableWithFrame(int strokeColor,int fillColor,int roundRadius){
        int strokeWidth = DensityUtil.dp2px(SystemStaticInstanceManager.getmApplicationContext(),1); // 1dp 边框宽度
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);    //内部填充颜色
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor); //边框颜色
        return  gd;
    }

    public static Drawable getRoundCornerDrawableWithFrame(int strokeColor,int fillColor,int roundRadius,int strokeWidth){
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);    //内部填充颜色
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor); //边框颜色
        return  gd;
    }
}
