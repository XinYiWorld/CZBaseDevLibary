package xinyi.com.czdevmodule.net_test.retrofit.callback;


import xinyi.com.czdevmodule.net_test.retrofit.UserBean;

/**
 * Created by 陈章 on 2017/4/8 0008.
 * func:
 * 用户注册的回调
 */

public interface IUserRegistCallBack {
    void onRegistSucess(UserBean userBean);     //注册成功
    void onRegistFailure();                      //注册失败
}
