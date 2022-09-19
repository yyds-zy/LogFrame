package com.yyds.logframe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yyds.log.util.CrashUtils;
import com.yyds.log.util.DeviceDetailInfo;
import com.yyds.log.util.JsonUtils;
import com.yyds.log.util.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.v("MainActivity");
        LogUtils.v("111111111111");
        LogUtils.d("222222222222");
        LogUtils.i("333333333333");
        LogUtils.e("444444444444");
        LogUtils.w("555555555555");
        LogUtils.wtf("666666666666");

        JsonUtils.v("net data");

        CrashUtils.e("404");
    }
}