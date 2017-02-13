package com.xinyi.czbasedevtool.base.bean;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class BaseHttpResponseBean {
    private int result;
    private String data;

    public void setResult(int result) {
        this.result = result;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public int getResult() {
        return result;
    }

    public boolean OK(){
        return result == 0;
    }

    @Override
    public String toString() {
        return "BaseHttpResponseBean{" +
                "result=" + result +
                ", data='" + data + '\'' +
                '}';
    }
}
