package com.xinyi.czbasedevtool.base.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class JSONUtil {
    //FastJson转换JSON串为List
    public static <E> List<E> getArray(String json, Class<E> classz){
        return JSON.parseArray(json,classz);
    }
}
