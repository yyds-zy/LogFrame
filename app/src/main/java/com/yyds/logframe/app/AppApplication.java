package com.yyds.logframe.app;

import android.app.Application;
import com.yyds.log.manager.FlyingManager;
import com.yyds.log.util.DeviceDetailInfo;
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
        FlyingManager.getInstance().initFrame(application,BuildConfig.DEBUG);
        //输出设备信息
        String devicesInfo = DeviceDetailInfo.getDevicesInfo(application, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        LogUtils.wtf(devicesInfo);
    }
}
