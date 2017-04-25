package xinyi.com.czdevmodule.net_test.retrofit.action;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResultBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by 陈章 on 2017/4/8 0008.
 * func:
 * 用户相关的网络请求
 */

public interface UserAction {

    //用户注册
    @FormUrlEncoded
    @POST("index.php?g=people&m=user&a=register")
    Observable<BaseHttpResultBean> regist(@Field("phone") String phone, @Field("cat") int cat, @Field("passwd") String passwd);


    @Multipart
    @POST("index.php?g=people&m=company&a=company_save")
    Observable<BaseHttpResultBean> getOrSaveCompanyInfo(@Part() List<MultipartBody.Part> parts);
}
