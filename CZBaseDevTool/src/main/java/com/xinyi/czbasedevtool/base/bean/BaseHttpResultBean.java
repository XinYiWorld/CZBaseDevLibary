package com.xinyi.czbasedevtool.base.bean;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class BaseHttpResultBean extends BaseHttpResponseBean{
    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }


    //单独给用户的轻量级的result类，不返回data。
    public BaseHttpResponseBean getSimpleResultInfoBean(){
        return new BaseHttpResponseBean (getResult(),getMes());
    }
}
