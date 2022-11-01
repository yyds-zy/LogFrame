package com.yyds.log.util;

import android.util.Log;

/**
 * Created by 阿飞の小蝴蝶 on 2022/11/1
 * Describe:
 * println longLog
 */
public class LongLogUtils {

    public static void printlnLog(String tag,String msg) {
        if (tag == null || tag.length() == 0 || msg == null || msg.length() == 0) {
            return;
        }

        int maxLogLength = 1024 * 3;
        int length = msg.length();

        if (length <= maxLogLength) {
            Log.d(tag,msg);
        } else {
            while (msg.length() > maxLogLength) {
                String substring = msg.substring(0, maxLogLength);
                msg = msg.replace(substring, "");
                Log.d(tag,substring);
            }
            Log.d(tag,msg);  //打印剩余的日志
        }
    }
}
