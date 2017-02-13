package com.xinyi.czbasedevtool.base.manager.net_about;

import android.os.Environment;

import com.xinyi.czbasedevtool.base.bean.UploadFileWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
}
