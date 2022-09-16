package com.yyds.logframe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yyds.log.util.DeviceDetailInfo;
import com.yyds.log.util.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.v("MainActivity");
        LogUtils.wtf(DeviceDetailInfo.getDevicesInfo(this, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
    }
}