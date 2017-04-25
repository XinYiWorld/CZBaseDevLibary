package com.xinyi.czbasedevtool.base.interfaces.net_about;

import android.view.View;

import com.xinyi.czbasedevtool.base.bean.UploadFileWrapper;

import java.util.List;
import java.util.Map;

/**
 * 发起一系列的网络请求接口,不用判断网络状态。
 * author:Created by ChenZhang on 2016/6/22 0022.
 * function:
 */
public interface I_Real_RequestServer {
    //对服务器原始请求结果进行map映射
    /**
     *
     * @param serviceClass              Retrofit的Service的Class
     * @param methodName                Retrofit的method的Name
     * @param convertedClass            解析的目标的结果类Class
     * @param params                    method的参数
     * @param isTargetBeanAsList        解析的目标结果是否是一个List
     * @param <T>
     * @param <K>
     */
    <T,K> void  realRequestData(View executor, int requestCode, Class<T> serviceClass, String methodName, final Class<K> convertedClass, Object[] params, boolean... isTargetBeanAsList);

    //不对服务器原始请求结果进行map映射
    <T> void  realRequestDataWithOutConvert(View executor,int requestCode,Class<T> serviceClass, String methodName, Object[] params);

    //上传多个文件
    <T> void  realUploadFiles(View executor,int requestCode,Class<T> serviceClass, String methodName, List<UploadFileWrapper> uploadFileWrappers);

    //上传单个文件
    <T> void  realUploadOneFile(View executor,int requestCode,Class<T> serviceClass, String methodName, UploadFileWrapper uploadFileWrapper);

    //下载文件
    void  realDownloadFile(View executor,int requestCode,String fileUrl);

    //上传单个文件(同时可以上传字段参数)
    <T,K> void  realUpOneFileAndData(View executor, int requestCode, Class<T> serviceClass, String methodName, final Class<K> convertedClass, Map<String,String> params, UploadFileWrapper uploadFileWrapper, boolean... isTargetBeanAsList);

    //上传多个文件(同时可以上传字段参数)
    <T,K> void  realUpFilesAndData(View executor,int requestCode, Class<T> serviceClass, String methodName, final Class<K> convertedClass, Map<String,String> params,List<UploadFileWrapper> uploadFileWrappers, boolean... isTargetBeanAsList);
}
