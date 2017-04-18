package com.xinyi.czbasedevtool.base.manager.net_about;

import com.xinyi.czbasedevtool.base.utils.TLog;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 陈章 on 2017/4/7 0007.
 * func:
 * Token 生成器
 */

public class TokenGenerator {
    public static String encode(String string) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    //haopeixun2015-07-21#$@%!
    public static String getToken(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String MD5String = formatter.format(curDate);
        //KTLog.e("日期:" + MD5String);
        MD5String = "haopeixun" + MD5String + "#$@%!";
        try {
            MD5String = encode(MD5String);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TLog.i("token=" + MD5String);
        return MD5String;
    }
}
