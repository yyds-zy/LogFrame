package com.yyds.log.util;

import android.content.Context;
import android.util.Log;

import com.yyds.log.util.crash.CrashToFile;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public final class CrashUtils {

    private static final String TAG = "[flying-log]";
    private static boolean sIsDebug = false;

    private CrashUtils() {
        /* Protect from instantiations */
    }

    /**
     * 初始化,在自定义Application类调用
     *
     * @param isDebug 是否打印log,建议传入BuildConfig.DEBUG
     *                isDebug = true 打印 且 创建日志文件   false 不打印 但是 创建日志文件
     */
    public static CrashUtils with(Context context, boolean isDebug) {
        CrashToFile.init(context);
        sIsDebug = isDebug;
        return new CrashUtils();
    }

    private static boolean isDebuggable() {
        return sIsDebug;
    }

    public static void e(String errorMsg) {
        if (isDebuggable()) {
            Log.e(TAG,errorMsg);
        }
        CrashToFile.e(TAG, errorMsg);
    }

    /**
     * 自动删除日志
     * @return
     */
    public CrashUtils autoDeleteCrashFile () {
        // 判断是否超过设置日志文件保留时长
        if (CrashToFile.isDeleteLogFile()) {
            CrashToFile.deleteFile();
        }
        return this;
    }

    /**
     * 设置日志删除天数
     * @param days
     * @return
     */
    public CrashUtils setCrashDeleteDays(int days){
        CrashToFile.setFileSaveDays(days);
        return this;
    }
}
