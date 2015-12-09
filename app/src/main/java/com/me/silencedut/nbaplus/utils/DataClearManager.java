package com.me.silencedut.nbaplus.utils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
/**
 * Created by SlienceDut on 2015/12/15.
 */
public class DataClearManager {

    private static final String newsCacheurl = Environment.getExternalStorageDirectory().getAbsolutePath() + "nbaplus";
    private static final String imUrl = Environment.getExternalStorageDirectory().getAbsolutePath() + "";

    /**
     * 清除本应用内部缓存
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    public static double getInternalCacheSize(Context context) {
        Log.d("getApplicationDataSize", context.getCacheDir()+"kkkkk");
        return getDirSize(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    public static double getDatabasesSize(Context context) {
        return getDirSize(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用sharedPreference保存数据
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    public static double getSharedSize (Context context) {
        return getDirSize(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 清除本应用数据库
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除file下的内容
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    public static double getFilesSize(Context context) {
        return getDirSize(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 计算外部cache大小
     * @param context
     * @return
     */
    public static double getExternalCacheSize(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return getDirSize(context.getExternalCacheDir());
        }
        return 0;
    }


    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    public static String getCustomCacheSize(String filePath) {
        double size = getDirSize(new File(filePath));
        return String.format("%.1f", size);
    }

    /**
     * 清除本应用所有的数据
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        //cleanSharedPreference(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    public static void cleanApplicationData(Context context) {
        cleanApplicationData(context,newsCacheurl);
    }

    /**
     * 获得删除数据的大小，保存两位有效数字，并且返回字符串
     * @param context
     * @param filepath
     * @return
     */
    public static String getApplicationDataSize(Context context, String... filepath) {
        double size = 0;
        size += getInternalCacheSize(context);
        dirSizeAll = 0;
        getExternalCacheSize(context);
        size += dirSizeAll;
        for (String file : filepath) {
            dirSizeAll = 0;
            getCustomCacheSize(file);
            size += dirSizeAll;
        }
        return String.format("%.1f", size)+"M";
    }
//
    public static String getApplicationDataSize(Context context) {
        return getApplicationDataSize(context,newsCacheurl);
    }

    /**
     * 删除某文件夹下的文件，如果传入参数是个文件，酱不做处理
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (item.isFile()) {
                    item.delete();
                } else if (item.isDirectory()) {
                    deleteFilesByDirectory(item);
                }
            }
//            directory.delete();
        }
    }


    /**
     * 计算文件夹大小
     * @param dir
     * @return Mb
     */
    public static double getDirSize(File dir) {
        if (dir == null || !dir.exists()) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        double dirSize = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
                }
            }
        }
        dirSizeAll += dirSize / (1024 * 1024);
        return dirSizeAll;
    }

    private static double dirSizeAll = 0;
}
