package com.xinyi.czbasedevtool.base.utils;

import java.text.DecimalFormat;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class NumUtil {
    private NumUtil(){

    }

    public static String return2InString(String num){
        DecimalFormat df   =new  DecimalFormat("0.00");
        return df.format(num);
    }

    public static String return2InString(String num,int pointLength){
        DecimalFormat df   =new  DecimalFormat(getPointStr(pointLength));
        return df.format(num);
    }


    private static String getPointStr(int pointLength){
        StringBuilder sb = new StringBuilder();
        if(pointLength < 0){
            throw new RuntimeException("pointLength must be >= 0");
        }
        if(pointLength >= 0){
            sb.append("0");
        }

        if(pointLength >=1){
            sb.append(".0");
        }

        if (pointLength >=2){
            for (int i = 2; i <= pointLength; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }
}
