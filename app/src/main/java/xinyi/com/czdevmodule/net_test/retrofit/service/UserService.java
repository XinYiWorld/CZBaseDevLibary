package xinyi.com.czdevmodule.net_test.retrofit.service;

import android.content.Context;
import android.view.View;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResponseBean;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_Try_RequestServer;
import com.xinyi.czbasedevtool.base.utils.CZ_TextUtil;
import com.xinyi.czbasedevtool.base.utils.RegularUtil;
import com.xinyi.czbasedevtool.base.utils.ToastUtil;

import xinyi.com.czdevmodule.R;
import xinyi.com.czdevmodule.net_test.retrofit.UserBean;
import xinyi.com.czdevmodule.net_test.retrofit.action.UserAction;
import xinyi.com.czdevmodule.net_test.retrofit.callback.IUserRegistCallBack;
import xinyi.com.czdevmodule.net_test.retrofit.service.base.BaseService;

/**
 * Created by 陈章 on 2017/4/8 0008.
 * func:
 * 用户业务操作类
 */
public class UserService extends BaseService {
    private final int NET_REQUEST_CODE = 0X0001;
    private IUserRegistCallBack callBack;


    public UserService(Context mContext, I_Try_RequestServer httpRequester) {
        super(mContext, httpRequester);
    }

    public void setRegistCallBack(IUserRegistCallBack callBack) {
        this.callBack = callBack;
    }

    //用户注册
    public void regist(View view,UserBean user){
        httpRequester.requestData(view,NET_REQUEST_CODE, UserAction.class,"regist",UserBean.class,new Object[]{user.getTelephone(),user.getCat(),user.getPassword()},false);
    }

    //结果总站
    @Override
    public <T> void onSuccess(int requestCode, BaseHttpResponseBean codeInfoBean, T successBean) {
        String mes = codeInfoBean.getMes();
        if(!CZ_TextUtil.is_null_or_empty(mes)){
            ToastUtil.shortT(mContext,mes);
        }

        switch (requestCode){
            case NET_REQUEST_CODE:
                onRegistSuccess(codeInfoBean, (UserBean) successBean);
                break;
        }
    }


    //注册回调成功处理站
    public <T> void onRegistSuccess(BaseHttpResponseBean codeInfoBean, UserBean user) {
        if(codeInfoBean.OK()){
            if(callBack != null){
                callBack.onRegistSucess(user);
            }
        }else{
            if(callBack != null){
                callBack.onRegistFailure();
            }
        }
    }


    //注册校验
    public boolean checkRegistInput(String phone,String password){
        //手机号为空
        if(CZ_TextUtil.is_null_or_empty(phone)){
            ToastUtil.shortT(mContext, R.string.warn_input_phone_empty);
            return false;
        }

//        //验证码为空
//        if(CZ_TextUtil.is_null_or_empty(verifiCode)){
//            ToastUtil.shortT(mContext,R.string.warn_input_code_empty);
//            return false;
//        }

        //密码为空
        if(CZ_TextUtil.is_null_or_empty(password)){
            ToastUtil.shortT(mContext,R.string.warn_input_password_empty);
            return false;
        }

        //手机号不合法
        if(!RegularUtil.isPhoneNumber(phone)){
            ToastUtil.shortT(mContext,R.string.warn_input_phone_illegal);
            return false;
        }

        //密码不合法
        if(phone.length() < 6){
            ToastUtil.shortT(mContext,R.string.warn_input_phone_min_length);
            return false;
        }else if(phone.length() > 12){
            ToastUtil.shortT(mContext,R.string.warn_input_phone_max_length);
            return false;
        }
        return true;
    }
}


