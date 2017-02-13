package com.xinyi.czbasedevtool.base.utils.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.xinyi.czbasedevtool.base.utils.io.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片基本操作工具类
 * author:Created by ChenZhang on 2016/6/29 0029.
 * function:
 */
public class ImageCommonUtil {
    private static final String TAG = "ImageCommonUtil";

    /**
     * 拍照回调
     */
    public static final int REQUESTCODE_CAMERA = 1;// 拍照修改头像
    public static final int REQUESTCODE_ALBUM = 2;// 本地相册修改头像
    public static final int REQUESTCODE_CROP = 3;// 系统裁剪头像


    /**
     * 获取图片缓存路径
     * @param context
     * @return
     */
    private static String getImageCacheDir(Context context) {
        String dir = FileUtil.CACHE_DIR + "Image" + File.separator;
        File file = new File(dir);
        if (!file.exists()) file.mkdirs();
        return dir;
    }


    /**
     * 由资源id获取图片
     * @param context
     * @param resId
     * @return
     */

    public static Drawable getDrawableById(Context context, int resId) {

        if (context == null) {

            return null;

        }

        return context.getResources().getDrawable(resId);

    }



    /**

     * 由资源id获取位图

     *

     * @param context

     * @param resId

     * @return

     */

    public static Bitmap getBitmapById(Context context, int resId) {

        if (context == null) {

            return null;

        }

        return BitmapFactory.decodeResource(context.getResources(), resId);

    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmapByFile(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }


    /**

     * 将Bitmap转化为字节数组

     *

     * @param bitmap

     * @return

     */

    public static byte[] bitmap2byte(Bitmap bitmap) {

        ByteArrayOutputStream baos = null;

        try {

            baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] array = baos.toByteArray();

            baos.flush();

            baos.close();

            return array;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;



    }



    /**

     * 将byte数组转化为bitmap

     *

     * @param data

     * @return

     */

    public static Bitmap byte2bitmap(byte[] data) {

        if (null == data) {

            return null;

        }

        return BitmapFactory.decodeByteArray(data, 0, data.length);

    }



    /**

     * 将Drawable转化为Bitmap

     *

     * @param drawable

     * @return

     */

    public static Bitmap drawable2bitmap(Drawable drawable) {

        if (null == drawable) {

            return null;

        }

        int width = drawable.getIntrinsicWidth();

        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable

                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, width, height);

        drawable.draw(canvas);// 重点

        return bitmap;



    }



    /**

     * 将bitmap转化为drawable

     *

     * @param bitmap

     * @return

     */

    public static Drawable bitmap2Drawable(Bitmap bitmap) {

        if (bitmap == null) {

            return null;

        }

        return new BitmapDrawable(bitmap);

    }





    /**
     * 将bitmap位图保存到path路径下，图片格式为Bitmap.CompressFormat.PNG，质量为100
     * @param bitmap
     * @param path
     */
    public static boolean saveBitmap(Bitmap bitmap, String path) {

        try {

            File file = new File(path);

            File parent = file.getParentFile();

            if (!parent.exists()) {

                parent.mkdirs();

            }

            FileOutputStream fos = new FileOutputStream(file);

            boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();

            fos.close();

            return b;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

    }



    /**
     * 将bitmap位图保存到path路径下
     * @param bitmap
     * @param path
     *            保存路径-Bitmap.CompressFormat.PNG或Bitmap.CompressFormat.JPEG.PNG
     * @param format
     *            格式
     * @param quality
     *            Hint to the compressor, 0-100. 0 meaning compress for small
     *            size, 100 meaning compress for max quality. Some formats, like
     *            PNG which is lossless, will ignore the quality setting
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String path,

                                     Bitmap.CompressFormat format, int quality) {

        try {

            File file = new File(path);

            File parent = file.getParentFile();

            if (!parent.exists()) {

                parent.mkdirs();

            }

            FileOutputStream fos = new FileOutputStream(file);

            boolean b = bitmap.compress(format, quality, fos);

            fos.flush();

            fos.close();

            return b;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

    }
    /**
     * 获得圆角图片
     * @param bitmap
     * @param roundPx
     * @return
     */

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),

                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    /**
     * 获得带倒影的图片
     */

    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * rotate the bitmap
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }


    /**
     * 通过uri获取文件的绝对路径
     * @param uri
     * @return
     */
    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.managedQuery(uri, proj, // Which columns to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }
        return imagePath;
    }

	 /**
	 注意此方法要在子线程中执行
	 **/
	 public static boolean saveImageViewPicToAlbum(Context context,ImageView imgView){
        imgView.buildDrawingCache();
        Bitmap bitmap=imgView.getDrawingCache();

        File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/picture" );
        if(!dir.isFile()){
            dir.mkdir();
        }
        File file=new File(dir,System.currentTimeMillis() +".png" );


        boolean result = saveBitmap(bitmap, file.getAbsolutePath());
        if(result){
            //用广播通知相册进行更新相册

            Intent intent = new Intent(Intent. ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            Log.v(TAG, "------>通知相册更新成功");
            return true;
        }

        return false;
    }



    // 专为Android4.4设计的从Uri(如Intent拍照或者选取图片)获取文件绝对路径，以前的方法已不好使
    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }
    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }



}
