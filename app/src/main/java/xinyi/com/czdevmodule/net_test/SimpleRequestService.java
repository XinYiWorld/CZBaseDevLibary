package xinyi.com.czdevmodule.net_test;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResultBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public interface SimpleRequestService {
    @FormUrlEncoded
    @POST("index.php?g=people&m=user&a=register")
    Observable<BaseHttpResultBean> regist(@Field("phone") String phone, @Field("cat") int cat, @Field("passwd") String passwd);


//    @POST("index.php?g=people&m=user&a=register")
//    Observable<BaseHttpResultBean> regist(@Query("phone") String phone, @Query("cat") int cat, @Query("passwd") String passwd);


    @GET("index.php?g=apps&m=user&a=getAddressList")
    Observable<BaseHttpResultBean> test(@Query("uid") String uid);
//
//    @GET("?g=apps&m=index&a=getPatch")
//    Observable<BaseHttpResultBean> test();

    @GET("index.php?g=people&m=common&a=degree_get")
    Observable<BaseHttpResultBean> getDegreeList(@Query("name") String name);
}
