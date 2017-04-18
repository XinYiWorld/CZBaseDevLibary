package com.xinyi.czbasedevtool.base.utils.io;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xinyi.czbasedevtool.base.interfaces.listener.OnTaskDoneListener;
import com.xinyi.czbasedevtool.base.manager.SystemStaticInstanceManager;
import com.xinyi.czbasedevtool.base.utils.ExceptionUtil;
import com.xinyi.czbasedevtool.base.utils.TLog;
import com.xinyi.czbasedevtool.base.utils.io.concrete.PrintWriterStrategyWrite;
import com.xinyi.czbasedevtool.base.utils.io.concrete.StreamStrategyWrite;
import com.xinyi.czbasedevtool.base.utils.io.interfaces.Abs_StrategyWrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

/**文件操作工具类 (注意读写权限的添加)
 * 1-文件读写
 *
 * author:Created by ChenZhang on 2016/7/6 0006.
 * function:
 */
public class FileUtil {
    private static final String TAG = "FileUtil";
    //SD卡根目录
    public static  final String SDPATH=Environment.getExternalStorageDirectory()+ File.separator;

    //app存储文件根目录
    public static final String SAVE_ROOT_DIR = Environment.getExternalStorageDirectory() + File.separator
            + SystemStaticInstanceManager.getmApplicationContext().getPackageName() + File.separator;

    //缓存根目录
    public static final String CACHE_DIR = Environment.getExternalStorageDirectory() + File.separator
            + SystemStaticInstanceManager.getmApplicationContext().getPackageName() + File.separator + "cache"  + File.separator;


    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    public static boolean if_append = true;   //追加文本，不覆盖。



    private FileUtil(){
        ExceptionUtil.throwRunmtimeExcpetionInPrivateConstructor(FileUtil.class);
    }


    //文件读写操作
    /**
     * 如果文件不存在，就创建文件，如果文件的父文件夹不存在也会自动创建。
     * @param path 文件路径
     * @return
     */
    public static String createIfFileNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if(!parentFile.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())){
                    parentFile.mkdirs();
                }
                file.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return path;
    }


    /**
     * 如果文件不存在，就创建文件
     * @return
     */
    public static String createIfFileNotExist(File file) {
        return createIfFileNotExist(file.getAbsolutePath());
    }

    /**
     * 如果文件夹不存在则创建文件夹
     * @param folder
     * @return
     */
    public static boolean createIfDirectoryNotExist(File folder){
        if(!folder.exists()){
            folder.mkdirs();
            return true;
        }
        return false;
    }
    /**
     * 得到上一级目录
     * @param path
     * @return
     */
    public static File getParentDirectory(String path){
        return new File(path.substring(0, path.lastIndexOf("/")));
    }

    /**
     *  在SD卡上创建目录；
     * @param dirpath
     * @return
     */
    public File createDIR(String dirpath) {
        File dir=new File(SDPATH+dirpath);
        if(!dir.exists()){
            dir.mkdir();
        }
        return dir;
    }



    public static double getFileOrFolderSize(String filePath,int sizeType){
        File file=new File(filePath);
        long blockSize=0;
        try {
            if(file.isDirectory()){
                blockSize = getFolderSize(file);
            }else{
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TLog.e("获取文件大小","获取失败!");
        }
        return formatFileSize(blockSize, sizeType);
    }


    /**
     * 获取指定文件大小
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file,int... sizeType) throws Exception
    {
        long size = 0;
        if (file.exists()){
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        else{
            file.createNewFile();
            TLog.e("获取文件大小", "文件不存在!");
        }
        if(sizeType ==  null || sizeType.length == 0) {
            return size;
        }else{
            return (long) formatFileSize(size,sizeType[0]);
        }
    }

    /**
     * 获取指定文件夹
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFolderSize(File f) throws Exception
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++){
            if (flist[i].isDirectory()){
                size = size + getFolderSize(flist[i]);
            }
            else{
                size =size + getFileSize(flist[i]);
            }
        }
        return size;
    }


    /**
     * 转换文件大小,指定转换的类型
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double formatFileSize(long fileS,int sizeType)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong=Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    //读写文件

    public static void write(String txt,File target,int ...strategy){
        Abs_StrategyWrite executor = getAbs_strategyWrite(strategy);
        executor.write(txt,target);
    }

    public static void write(InputStream in,File target,int ...strategy){
        Abs_StrategyWrite executor = getAbs_strategyWrite(strategy);
        executor.write(in, target);
    }

    public static void write(byte[] bytes,File target,int ...strategy){
        Abs_StrategyWrite executor = getAbs_strategyWrite(strategy);
        executor.write(bytes,target);
    }


    @Nullable
    private static Abs_StrategyWrite getAbs_strategyWrite(int[] strategy) {
        Abs_StrategyWrite executor = null;
        if(strategy == null || strategy.length == 0) {
            executor = new StreamStrategyWrite();
        }else {
            if (strategy[0] == Abs_StrategyWrite.STREAM_WRITE) {
                executor = new StreamStrategyWrite();
            } else if (strategy[0] == Abs_StrategyWrite.PRINT_WRITE) {
                executor = new PrintWriterStrategyWrite();
            }
        }
        return executor;
    }


    public static void copy(final File sourceFile,File targetFile, final boolean ... deleteSourceFile){
        Abs_StrategyWrite executor =  new StreamStrategyWrite();
        executor.copy(sourceFile, targetFile, new OnTaskDoneListener() {
            @Override
            public void done(Object obj) {
                TLog.i(TAG, "done: ");
                if(deleteSourceFile != null && deleteSourceFile.length >0 && deleteSourceFile[0]){
                    sourceFile.deleteOnExit();
                }
            }
        });

    }

    //对象序列化
    public <T> void writeObj(T obj){
        File file = getObjCacheFile(obj);
        FileOutputStream fos = null;
        ObjectOutput out = null;
        try {
            createIfFileNotExist(file);
            fos = new FileOutputStream(file,false);
            out = new ObjectOutputStream(fos);
            out.writeObject(obj);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> T readObj(Class<T> tClass){
        File  file = getObjCacheFile(tClass);
        FileInputStream fis  = null;
        ObjectInput in  = null;
        createIfFileNotExist(file);
        try {
            in = new ObjectInputStream(fis);
            return (T) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @NonNull
    private <T> File getObjCacheFile(T obj) {
        return new File(CACHE_DIR + obj.getClass().getName());
    }

    @NonNull
    private <T> File getObjCacheFile(Class<T> tClass) {
        return new File(CACHE_DIR + tClass.getName());
    }

}
