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
        boolean check = check(input, "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,2,5-9])|(177))\\d{8}$");
        if(!check){
            check = (input.length() == 11);
        }
        return check;
    }

    public static boolean isNumeric(String str){
        return check(str,"[0-9]*");
    }

    public static boolean isEmail(String email){
        return check(email,"^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    public static boolean check(String input ,String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher isNum = pattern.matcher(input);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
