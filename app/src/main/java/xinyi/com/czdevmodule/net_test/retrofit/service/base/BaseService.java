package xinyi.com.czdevmodule.net_test.retrofit.service.base;

import android.content.Context;
import android.view.View;

import com.xinyi.czbasedevtool.base.interfaces.net_about.I_HttpResultHandler;
import com.xinyi.czbasedevtool.base.interfaces.net_about.I_Try_RequestServer;
import com.xinyi.czbasedevtool.base.manager.net_about.RetrofitClient;

/**
 * Created by 陈章 on 2017/4/8 0008.
 * func:
 * 设置token 、baseurl
 */
public abstract class BaseService  implements I_HttpResultHandler {
    public static  String BASE_URL = "http://app.bestbeijing.top/";
    protected I_Try_RequestServer httpRequester;
    protected Context mContext;
    public BaseService() {
        RetrofitClient.setHasToken(true);
        RetrofitClient.setBaseUrl(BASE_URL);
    }

    public BaseService(Context mContext, I_Try_RequestServer httpRequester) {
        this();
        this.mContext = mContext;
        this.httpRequester = httpRequester;
        httpRequester.bindHttpResultHandler(this);
    }

    @Override
    public void setState(View view, boolean b) {

    }
}
