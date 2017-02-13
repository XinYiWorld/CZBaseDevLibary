package com.xinyi.czbasedevtool.base.utils;

import com.xinyi.czbasedevtool.base.utils.time.TimeUtil;

/**
 * author:Created by ChenZhang on 2016/6/20 0020.
 * function:
 */
public class ExceptionUtil {
    public static void throwRunmtimeExcpetionInPrivateConstructor(Class classz){
        throw new RuntimeException(classz.getName() + "can't be init");
    }

    public static void throwNullPointerException(Object obj){
        if(obj == null){
            throw new NullPointerException("please init " + obj.getClass().getName());
        }
    }

    public static void throwNullPointerException(Object obj,ExceptionHappenListener exceptionHappenListener){
        if(obj == null){
            if(exceptionHappenListener != null){
                exceptionHappenListener.doNext();
            }
            throw new NullPointerException("please init " + obj.getClass().getName());
        }
    }

    public interface ExceptionHappenListener{
        void doNext();
    }

    public static String collectExceptionInfo(Throwable throwable){
        StringBuilder sb = new StringBuilder();
        sb.append(CZ_TextUtil.divider + CZ_TextUtil.CR
                + TimeUtil.getCurrentTimeInString() + CZ_TextUtil.CR + throwable.toString()
                );
        sb.append(CZ_TextUtil.CR + "Detail Info:");
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            sb.append(CZ_TextUtil.CR + throwable.getStackTrace()[i]);
        }
        sb.append(CZ_TextUtil.CR + CZ_TextUtil.divider + CZ_TextUtil.CR);
        return sb.toString();
    }
}
