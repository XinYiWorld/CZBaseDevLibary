package com.xinyi.czbasedevtool.base.bean;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 * //TODO 对于不同项目，可能响应返回的数据格式不一样。
 */
public class BaseHttpResponseBean {
    private int result;                     //状态码
    private String mes;               //状态码描述

    public BaseHttpResponseBean() {
    }

    public BaseHttpResponseBean(int result, String mes) {
        this.result = result;
        this.mes = mes;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public boolean OK(){
        return result == 0;
    }


}
