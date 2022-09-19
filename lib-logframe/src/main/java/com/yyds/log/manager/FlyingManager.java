package com.yyds.log.manager;

import android.app.Application;
import com.yyds.log.util.CrashUtils;
import com.yyds.log.util.JsonUtils;
import com.yyds.log.util.LogUtils;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public class FlyingManager {

    private static FlyingManager instance;
    private FlyingManager(){}

    public static FlyingManager getInstance() {
        if (instance == null) {
            synchronized (FlyingManager.class) {
                if (instance == null) {
                    instance = new FlyingManager();
                }
            }
        }
        return instance;
    }

    public void initFrame(Application application,boolean isDebug,int logLevel,int logSaveDays) {
        // register本地异常捕捉
        CrashHandler.register(application);
        //初始化日志框架
        LogUtils.with(application, isDebug)
                .setLogLevel(logLevel)
                .setLogDeleteDays(logSaveDays)
                .autoDeleteLogFile();

        //初始化异常日志框架
        CrashUtils.with(application,isDebug)
                .setCrashDeleteDays(logSaveDays)
                .autoDeleteCrashFile();

        //初始化http请求数据框架
        JsonUtils.with(application,isDebug)
                .setJsonDeleteDays(logSaveDays)
                .autoDeleteJsonFile();
    }

    public void initLogFrame (Application application,boolean isDebug,int logLevel,int logSaveDays){
        //初始化日志框架
        LogUtils.with(application, isDebug)
                .setLogLevel(logLevel)
                .setLogDeleteDays(logSaveDays)
                .autoDeleteLogFile();
    }

    public void initCrashFrame(Application application,boolean isDebug,int logSaveDays) {
        // register本地异常捕捉
        CrashHandler.register(application);
        //初始化异常日志框架
        CrashUtils.with(application,isDebug)
                .setCrashDeleteDays(logSaveDays)
                .autoDeleteCrashFile();
    }

    public void initJsonFrame(Application application,boolean isDebug,int logSaveDays) {
        //初始化http请求数据框架
        JsonUtils.with(application,isDebug)
                .setJsonDeleteDays(logSaveDays)
                .autoDeleteJsonFile();
    }
}
