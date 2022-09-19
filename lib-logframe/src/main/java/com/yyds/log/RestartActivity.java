package com.yyds.log;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public class RestartActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, RestartActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);
        restart(this);
        finish();
    }

    public static void restart(Context context) {
//        Intent intent;
//        if (true) {
//            // 如果是未登录的情况下跳转到闪屏页
//            intent = new Intent(context, SplashActivity.class);
//        } else {
//            // 如果是已登录的情况下跳转到首页
//            intent = new Intent(context, HomeActivity.class);
//        }
//
//        if (!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        context.startActivity(intent);
    }
}
