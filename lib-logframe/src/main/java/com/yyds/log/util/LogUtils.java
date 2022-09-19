package com.yyds.log.util;

import android.content.Context;
import android.util.Log;

import com.yyds.log.util.log.LogToFile;

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
     * 初始化,在自定义Application类调用
     *
     * @param isDebug 是否打印log,建议传入BuildConfig.DEBUG
     *                isDebug = true 打印 且 创建日志文件   false 不打印 但是 创建日志文件
     */
    public static LogUtils with(Context context,boolean isDebug) {
        LogToFile.init(context);
        sIsDebug = isDebug;
        return new LogUtils();
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
        if (mLog_level <= ERROR) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.e(TAG,log);
            }
            LogToFile.e(TAG, log);
        }
    }

    public static void e(Throwable e) {
        if (e == null)
            return;
        e(e.getMessage(), e);
    }

    public static void e(String message, Throwable e) {
        if (mLog_level <= ERROR) {
            getMethodNames(e.getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.e(TAG,log,e);
            }
            LogToFile.e(TAG, log + e.getMessage());
        }
    }

    public static void i(String message) {
        if (mLog_level <= INFO) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.i(TAG,log);
            }
            LogToFile.i(TAG, log);
        }
    }

    public static void d(String message) {
        if (mLog_level <= DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.d(TAG,log);
            }
            LogToFile.d(TAG, log);
        }
    }

    public static void v(String message) {
        if (mLog_level <= VERBOSE) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.v(TAG,log);
            }
            LogToFile.v(TAG, log);
        }
    }

    public static void w(String message) {
        if (mLog_level <= WARN) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.w(TAG,log);
            }
            LogToFile.w(TAG, log);
        }
    }

    public static void wtf(String message) {
        if (mLog_level <= FAILTAL) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.wtf(TAG,log);
            }
            LogToFile.wtf(TAG, log);
        }
    }

    /**
     * 设置日志打印等级，login之前调用，不设置默认打印等级为0
     *
     * @param log_level 日志等级0~5, 0=verbose,1=debug,2=info,3=warn,4=error 5=failtal
     */
    public LogUtils setLogLevel(int log_level) {
        mLog_level = log_level;
        return this;
    }

    /**
     *
     * 输出设备详细信息
     * @param deviceInfo
     * @return
     */
    public LogUtils systemOutPutDeviceInfo(String deviceInfo) {
        wtf(deviceInfo);
        return this;
    }

    /**
     * 自动删除日志
     * @return
     */
    public LogUtils autoDeleteLogFile () {
        // 判断是否超过设置日志文件保留时长
        if (LogToFile.isDeleteLogFile()) {
            LogToFile.deleteFile();
        }
        return this;
    }

    /**
     * 设置日志删除天数
     * @param days
     * @return
     */
    public LogUtils setLogDeleteDays(int days){
        LogToFile.setFileSaveDays(days);
        return this;
    }
}
