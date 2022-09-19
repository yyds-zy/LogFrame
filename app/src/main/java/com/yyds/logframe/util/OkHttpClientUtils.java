package com.yyds.logframe.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public class OkHttpClientUtils {

    private static OkHttpClient mClient;

    public synchronized static OkHttpClient getClient() {
        if (mClient == null) {
            mClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();
        }
        return mClient;
    }
}
