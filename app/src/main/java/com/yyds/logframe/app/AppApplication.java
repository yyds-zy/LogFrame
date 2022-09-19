package com.yyds.logframe.app;

import android.app.Application;

import com.yyds.log.manager.CrashHandler;
import com.yyds.log.util.CrashUtils;
import com.yyds.log.util.DeviceDetailInfo;
import com.yyds.log.util.JsonUtils;
import com.yyds.log.util.log.LogToFile;
import com.yyds.log.util.LogUtils;
import com.yyds.logframe.BuildConfig;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/16
 * Describe:
 */
public class AppApplication extends Application {

    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // register本地异常捕捉
        CrashHandler.register(application);
        //初始化日志框架
        LogUtils.with(application,BuildConfig.DEBUG)
                .setLogLevel(5).systemOutPutDeviceInfo(DeviceDetailInfo.getDevicesInfo(
                application, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE))
                .setLogDeleteDays(5)
                .autoDeleteLogFile();

        //初始化异常日志框架
        CrashUtils.with(application,BuildConfig.DEBUG)
                .setCrashDeleteDays(5)
                .autoDeleteCrashFile();

        //初始化http请求数据框架
        JsonUtils.with(application,BuildConfig.DEBUG)
                .setJsonDeleteDays(5)
                .autoDeleteJsonFile();
    }
}
