package com.xinyi.czbasedevtool.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class MD5Util {
    /**
     * 将字符串转换成MD5
     * @param key
     * @return
     */
    public static String getMD5(String key) {
        StringBuffer sb = new StringBuffer();
        // 信息摘要器MessageDigest
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("md5");

            byte[] bs = digest.digest(key.getBytes());
            for (byte b : bs) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1) {
                    sb.append("0");
                }
                sb.append(hexString);
                System.out.println(sb);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 得到文件的MD5
     * @param file
     * @return
     */
    public static String getMD5ForFile(File file)
    {
        //文件不存在则返回空
        if(!file.exists())
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        InputStream input = null;
        try {
            //生成摘要器
            MessageDigest md = MessageDigest.getInstance("md5");

            //读取文件更新摘要器
            input = new FileInputStream(file);

            byte[] buf = new byte[1024*100];
            int len = 0;
            while((len = input.read(buf))!= -1)
            {
                md.update(buf, 0, len);
            }

            //汇总特征值
            byte[] final_byte = md.digest();	//数组的长度始终是16

            for (byte b : final_byte) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1) {
                    sb.append("0");
                }
                sb.append(hexString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            if(input != null)
            {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString().trim();
    }
}
