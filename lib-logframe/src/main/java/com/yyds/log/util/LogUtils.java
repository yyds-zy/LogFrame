package com.yyds.log.util;

import android.content.Context;

public class LogUtils {

    private static final String TAG = "[flying-log]";
    private static boolean sIsDebug = false;

    static String fileName;
    static String methodName;
    static int lineNumber;
    private static int mLog_level = 0;
    //日志级别
    private static int VERBOSE = 0;
    private static int DEBUG = 1;
    private static int INFO = 2;
    private static int WARN = 3;
    private static int ERROR = 4;
    private static int FAILTAL = 5;

    private LogUtils() {
        /* Protect from instantiations */
    }

    /**
     * 设置日志打印等级，login之前调用，不设置默认打印等级为0
     *
     * @param log_level 日志等级0~5, 0=verbose,1=debug,2=info,3=warn,4=error 5=failtal
     */
    public static void setLogLevel(int log_level) {
        mLog_level = log_level;
    }

    /**
     * 初始化,在自定义Application类调用
     *
     * @param isDebug 是否打印log,建议传入BuildConfig.DEBUG
     *                isDebug = true 打印 且 保存   false 不打印且不创建日志文件
     */
    public static void initialize(Context context,boolean isDebug) {
        if (isDebug) {
            LogToFile.init(context);
        }
        sIsDebug = isDebug;
    }

    private static boolean isDebuggable() {
        return sIsDebug;
    }

    private static String createLog(String log) {

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(fileName);//文件名
        builder.append(" -> ");
        builder.append(methodName);//方法名
        builder.append(":");
        builder.append(lineNumber);//代码行
        builder.append("]");
        builder.append(log);
        return builder.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        if (sElements != null && sElements.length > 0) {
            fileName = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
        }
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;
        if (mLog_level > ERROR)
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        LogToFile.e(TAG, createLog(message));
    }

    public static void e(Throwable e) {
        if (e == null)
            return;
        e(e.getMessage(), e);
    }

    public static void e(String message, Throwable e) {
        if (!isDebuggable())
            return;
        if (mLog_level > ERROR)
            return;

        // Throwable instance must be created before any methods
        getMethodNames(e.getStackTrace());
        LogToFile.e(TAG, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;
        if (mLog_level > INFO)
            return;

        getMethodNames(new Throwable().getStackTrace());
        LogToFile.i(TAG, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;
        if (mLog_level > DEBUG)
            return;

        getMethodNames(new Throwable().getStackTrace());
        LogToFile.d(TAG, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;
        if (mLog_level > VERBOSE)
            return;

        getMethodNames(new Throwable().getStackTrace());
        LogToFile.v(TAG, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;
        if (mLog_level > WARN)
            return;

        getMethodNames(new Throwable().getStackTrace());
        LogToFile.w(TAG, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;
        if (mLog_level > FAILTAL)
            return;

        getMethodNames(new Throwable().getStackTrace());
        LogToFile.wtf(TAG, createLog(message));
    }
}
