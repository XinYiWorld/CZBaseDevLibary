package com.xinyi.czbasedevtool.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class RegularUtil {
    public static boolean isPhoneNumber(String input){
        String regex="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(input).matches();
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
