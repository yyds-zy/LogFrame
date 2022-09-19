package com.yyds.log.util;

import android.content.Context;
import android.util.Log;

import com.yyds.log.util.crash.CrashToFile;
import com.yyds.log.util.json.JsonToFile;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public class JsonUtils {

    private static final String TAG = "[flying-log]";
    private static boolean sIsDebug = false;

    private JsonUtils() {
        /* Protect from instantiations */
    }

    /**
     * 初始化,在自定义Application类调用
     *
     * @param isDebug 是否打印log,建议传入BuildConfig.DEBUG
     *                isDebug = true 打印 且 创建日志文件   false 不打印 但是 创建日志文件
     */
    public static JsonUtils with(Context context, boolean isDebug) {
        JsonToFile.getInstance().init(context);
        sIsDebug = isDebug;
        return new JsonUtils();
    }

    private static boolean isDebuggable() {
        return sIsDebug;
    }

    public static void v(String errorMsg) {
        if (isDebuggable()) {
            Log.v(TAG,errorMsg);
        }
        JsonToFile.getInstance().v(TAG, errorMsg);
    }

    /**
     * 自动删除日志
     * @return
     */
    public JsonUtils autoDeleteJsonFile () {
        // 判断是否超过设置日志文件保留时长
        if (JsonToFile.getInstance().isDeleteLogFile()) {
            JsonToFile.getInstance().deleteFile();
        }
        return this;
    }

    /**
     * 设置日志删除天数
     * @param days
     * @return
     */
    public JsonUtils setJsonDeleteDays(int days){
        JsonToFile.getInstance().setFileSaveDays(days);
        return this;
    }
}
