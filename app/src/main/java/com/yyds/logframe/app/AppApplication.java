package com.yyds.logframe.app;

import android.app.Application;
import android.os.Build;

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
        //init log frame
        LogUtils.initialize(application,BuildConfig.DEBUG);
        //setting log level
        LogUtils.setLogLevel(0);
        LogUtils.v("69696969696");
        LogUtils.d("69696969696");
        LogUtils.i("69696969696");
        LogUtils.e("69696969696");
        LogUtils.w("69696969696");
        LogUtils.wtf("69696969696");
    }
}
