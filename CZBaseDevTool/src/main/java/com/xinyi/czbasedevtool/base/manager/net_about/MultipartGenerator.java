package com.xinyi.czbasedevtool.base.manager.net_about;

import com.xinyi.czbasedevtool.base.bean.UploadFileWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Multipart 类型生成器
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public class MultipartGenerator {
    private MultipartGenerator(){
        throw new RuntimeException("MultipartGenerator can't be init");
    }

    public static MultipartBody.Part generate(UploadFileWrapper uploadFileWrapper){
        if (uploadFileWrapper == null) return null;

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), uploadFileWrapper.getFile());

        return  MultipartBody.Part.createFormData(uploadFileWrapper.getFormName(), uploadFileWrapper.getFile().getName(), requestFile);
    }

    public static List<MultipartBody.Part> generate(List<UploadFileWrapper> uploadFileWrappers){
        if (uploadFileWrappers == null || uploadFileWrappers.size() == 0) return null;
        List<MultipartBody.Part> parts = new ArrayList<>(uploadFileWrappers.size());
        for (int i = 0; i <uploadFileWrappers.size(); i++) {
            parts.add(generate(uploadFileWrappers.get(i)));
        }
        return parts;
    }

    public static List<MultipartBody.Part> generate(Map<String,Object> params, List<UploadFileWrapper> uploadFileWrappers){
        List<MultipartBody.Part> parts = new ArrayList<>(uploadFileWrappers.size());
        //添加字段part
        if(params != null){
            Set<String> keySet = params.keySet();
            for (Iterator<String> it = keySet.iterator(); it.hasNext();){
                String key = it.next();
                Object value = params.get(key);
                parts.add(MultipartBody.Part.createFormData(key,String.valueOf(value)));
            }
        }

        if (uploadFileWrappers == null || uploadFileWrappers.size() == 0) return parts;     //不传图片

        //添加图片part
        for (int i = 0; i <uploadFileWrappers.size(); i++) {
            parts.add(generate(uploadFileWrappers.get(i)));
        }
        return parts;
    }

}
