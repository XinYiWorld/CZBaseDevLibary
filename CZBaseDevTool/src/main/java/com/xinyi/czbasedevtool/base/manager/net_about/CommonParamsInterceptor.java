package com.xinyi.czbasedevtool.base.manager.net_about;

import com.xinyi.czbasedevtool.base.utils.TLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by CZ on 2017/4/7 0007.
 * func:
 * POST请求公共参数添加拦截器
 */
public class CommonParamsInterceptor implements Interceptor {
    private static final String TAG = "CommonParamsInterceptor";
    private Map<String, String> paramsMap;

    public CommonParamsInterceptor() {
        paramsMap = new HashMap<>();
    }

    public void addParams(String key, String value) {
        paramsMap.put(key, value);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request orgRequest = chain.request();
        String method = orgRequest.method();
        RequestBody body = orgRequest.body();
        //收集请求参数，方便调试
        StringBuilder paramsBuilder = new StringBuilder();

        if (body != null) {

            RequestBody newBody = null;

            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body, paramsBuilder);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder);
            }


            if (null != newBody) {
                //打印参数
                TLog.i(TAG, paramsBuilder.toString());

                Request newRequest = orgRequest.newBuilder()
                        .url(orgRequest.url())
                        .method(method, newBody)
                        .build();

                return chain.proceed(newRequest);
            }
        }
        return chain.proceed(orgRequest);
    }

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        Set<String> keySet = paramsMap.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = paramsMap.get(key);
            builder.addFormDataPart(key, value);
            paramsBuilder.append(key + "=" + value);
        }
        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }

        return builder.build();
    }

    /**
     * 为FormBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder) {
        FormBody.Builder builder = new FormBody.Builder();

        Set<String> keySet = paramsMap.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = paramsMap.get(key);
            builder.add(key, value);
            paramsBuilder.append(key + "=" + value);
        }

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
            paramsBuilder.append("&");
            paramsBuilder.append(body.name(i));
            paramsBuilder.append("=");
            paramsBuilder.append(body.value(i));
        }

        return builder.build();
    }
}