package com.yyds.log.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import com.yyds.log.manager.ThreadPoolManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/16
 * Describe:
 */
public class DeviceDetailInfo {

    /**
     * 获取设备详细信息
     *
     * @param context
     * @return
     */
    public static String getDevicesInfo(Context context, String versionName, int versionCode) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float smallestWidth = Math.min(screenWidth, screenHeight) / displayMetrics.density;

        String targetResource;
        if (displayMetrics.densityDpi > 480) {
            targetResource = "xxxhdpi";
        } else if (displayMetrics.densityDpi > 320) {
            targetResource = "xxhdpi";
        } else if (displayMetrics.densityDpi > 240) {
            targetResource = "xhdpi";
        } else if (displayMetrics.densityDpi > 160) {
            targetResource = "hdpi";
        } else if (displayMetrics.densityDpi > 120) {
            targetResource = "mdpi";
        } else {
            targetResource = "ldpi";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("设备品牌：\t").append(Build.BRAND)
                .append("\n设备型号：\t").append(Build.MODEL)
                .append("\n设备类型：\t").append(isTablet(context) ? "平板" : "手机");

        builder.append("\n屏幕宽高：\t").append(screenWidth).append(" x ").append(screenHeight)
                .append("\n屏幕密度：\t").append(displayMetrics.densityDpi)
                .append("\n密度像素：\t").append(displayMetrics.density)
                .append("\n目标资源：\t").append(targetResource)
                .append("\n最小宽度：\t").append((int) smallestWidth);

        builder.append("\n安卓版本：\t").append(Build.VERSION.RELEASE)
                .append("\nAPI 版本：\t").append(Build.VERSION.SDK_INT)
                .append("\nCPU 架构：\t").append(Build.SUPPORTED_ABIS[0]);

        builder.append("\n应用版本：\t").append(versionName)
                .append("\n版本代码：\t").append(versionCode);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            builder.append("\n首次安装：\t").append(dateFormat.format(new Date(packageInfo.firstInstallTime)))
                    .append("\n最近安装：\t").append(dateFormat.format(new Date(packageInfo.lastUpdateTime)))
                    .append("\n崩溃时间：\t").append(dateFormat.format(new Date()));

            if (packageInfo.requestedPermissions != null) {
                List<String> permissions = Arrays.asList(packageInfo.requestedPermissions);
                if (permissions.contains(Permission.READ_EXTERNAL_STORAGE) || permissions.contains(Permission.WRITE_EXTERNAL_STORAGE)) {
                    builder.append("\n存储权限：\t").append(isGranted(context, Permission.Group.STORAGE) ? "已获得" : "未获得");
                }

                if (permissions.contains(Permission.ACCESS_FINE_LOCATION) || permissions.contains(Permission.ACCESS_COARSE_LOCATION)) {
                    builder.append("\n定位权限：\t");
                    if (isGranted(context, Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)) {
                        builder.append("精确、粗略");
                    } else {
                        if (isGranted(context, Permission.ACCESS_FINE_LOCATION)) {
                            builder.append("精确");
                        } else if (isGranted(context, Permission.ACCESS_COARSE_LOCATION)) {
                            builder.append("粗略");
                        } else {
                            builder.append("未获得");
                        }
                    }
                }

                if (permissions.contains(Permission.CAMERA)) {
                    builder.append("\n相机权限：\t").append(isGranted(context, Permission.CAMERA) ? "已获得" : "未获得");
                }

                if (permissions.contains(Permission.RECORD_AUDIO)) {
                    builder.append("\n录音权限：\t").append(isGranted(context, Permission.RECORD_AUDIO) ? "已获得" : "未获得");
                }

                if (permissions.contains(Permission.SYSTEM_ALERT_WINDOW)) {
                    builder.append("\n悬浮窗权限：\t").append(isGranted(context, Permission.SYSTEM_ALERT_WINDOW) ? "已获得" : "未获得");
                }

                if (permissions.contains(Permission.REQUEST_INSTALL_PACKAGES)) {
                    builder.append("\n安装包权限：\t").append(isGranted(context, Permission.REQUEST_INSTALL_PACKAGES) ? "已获得" : "未获得");
                }

                if (permissions.contains(Manifest.permission.INTERNET)) {
                    ThreadPoolManager.getInstance().execute(() -> {
                        try {
                            InetAddress.getByName("www.baidu.com");
                            builder.append("正常");
                            LogUtils.i("当前网络访问：正常");
                        } catch (UnknownHostException ignored) {
                            builder.append("异常");
                            LogUtils.i("当前网络访问：异常");
                        }
                    });
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return builder.toString();
    }

    /**
     * 判断当前设备是否是平板
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isGranted(Context context, String... permissions) {
        return isGranted(context, PermissionUtils.asArrayList(permissions));
    }

    public static boolean isGranted(Context context, List<String> permissions) {
        return PermissionUtils.isGrantedPermissions(context, permissions);
    }
}
