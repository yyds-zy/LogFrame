package com.yyds.logframe.app;

import android.app.Application;

import com.yyds.log.util.DeviceDetailInfo;
import com.yyds.log.util.LogToFile;
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
        //初始化日志框架
        LogUtils.initialize(application,BuildConfig.DEBUG);
        //设置日志级别
        LogUtils.setLogLevel(5);
        //输出设备详细信息
        LogUtils.wtf(DeviceDetailInfo.getDevicesInfo(this, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
        // 判断是否超过设置日志文件保留时长
        if (LogToFile.isDeleteLogFile()) {
            LogToFile.deleteFile();
        }
    }
}
