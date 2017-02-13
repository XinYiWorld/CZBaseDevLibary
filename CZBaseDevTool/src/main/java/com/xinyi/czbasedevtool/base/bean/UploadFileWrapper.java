package com.xinyi.czbasedevtool.base.bean;

import java.io.File;

/**
 * OkHttp multipart文件上传包装类
 * author:Created by ChenZhang on 2016/6/23 0023.
 * function:
 */
public class UploadFileWrapper {
    private File file;
    private String formName;

    public UploadFileWrapper(File file, String formName) {
        this.file = file;
        this.formName = formName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
