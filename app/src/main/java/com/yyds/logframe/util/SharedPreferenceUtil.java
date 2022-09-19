package com.yyds.logframe.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yyds.logframe.app.AppApplication;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/16
 * Describe:
 */
public class SharedPreferenceUtil {

    private static volatile SharedPreferenceUtil sInstance;
    private static ConcurrentHashMap<String, SharedPreferences> sSpMap = new ConcurrentHashMap<String, SharedPreferences>();
    private SharedPreferenceUtil() {
    }

    public static SharedPreferenceUtil getInstance() {
        if (sInstance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreferenceUtil();
                }
            }
        }
        return sInstance;
    }

    public synchronized boolean getBoolean(String name, String key) {
        checkForNullKey(name);
        checkForNullKey(key);
        return getBoolean(name, key, false);
    }

    public synchronized boolean getBoolean(String name, String key, boolean def) {
        checkForNullKey(name);
        checkForNullKey(key);
        return getSharedPreferences(name).getBoolean(key, def);
    }

    public synchronized void putBoolean(String name, String key, boolean value) {
        checkForNullKey(name);
        checkForNullKey(key);
        getSharedPreferences(name).edit().putBoolean(key, value).apply();
    }

    public void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    private SharedPreferences getSharedPreferences(String name) {
        if (sSpMap.containsKey(name)) {
            return sSpMap.get(name);
        } else {
            SharedPreferences sharedPreferences = AppApplication.application.getSharedPreferences(name, Context.MODE_PRIVATE);
            sSpMap.put(name, sharedPreferences);
            return sharedPreferences;
        }
    }
}
