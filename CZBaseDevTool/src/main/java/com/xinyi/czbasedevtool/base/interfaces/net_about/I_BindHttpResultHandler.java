package com.xinyi.czbasedevtool.base.interfaces.net_about;

/**
 * Created by 陈章 on 2017/4/7 0007.
 * func:
 * 绑定多个结果处理器
 * 除了Activity之外，经常需要把数据访问及处理的逻辑封装到业务类中。
 * 但是总体上网络框架是基于Activity的，所以需要作这个扩展。
 */

public interface I_BindHttpResultHandler {
    void bindHttpResultHandler(I_HttpResultHandler i_httpResultHandler);
}
