package com.xinyi.czbasedevtool.base.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;

/**
 * 系统工具类
 * author:Created by ChenZhang on 2016/7/13 0013.
 * function:
 */
public class SystemUtil {

    public static void  call(Context context, String phoneNumber) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
    }

}
