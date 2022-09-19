package com.yyds.log.util.base;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public abstract class BaseFile {

    public static final String TAG = "[flying-log]";

    public static final char VERBOSE = 'v';
    public static final char DEBUG = 'd';
    public static final char INFO = 'i';
    public static final char WARN = 'w';
    public static final char ERROR = 'e';
    public static final char WTF = 'f';

    public static String logPath = null;//log日志存放路径
    public static SimpleDateFormat dateFormatLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);//日期格式;
    public static SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyyy-MM-dd", Locale.US);//日期格式;
    public static List<File> mFileList = new ArrayList<>();
    public static Date date = new Date();//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;
    public static int FILE_SAVE_DAYS = 7;//日志文件保留时长  默认7天
    public static Context mContent;

    //设置日志文件保留时长
    public abstract void setFileSaveDays(int saveDays);

    //获取日志文件保留时长
    public abstract int getFileSaveDays();

    //获得文件存储路径
    public abstract String getFilePath(Context context);

    //初始化，须在使用之前设置
    public abstract void init(Context context);

    //文件是否存在
    public abstract boolean isExistsFile(Context context);

    //将log信息写入文件中
    public abstract void writeToFile(char type, String tag, String msg);

    //获取.log文件列表
    public abstract List<File> getFileList();

    //是否删除日志文件
    public abstract boolean isDeleteLogFile();

    //删除日志文件
    public abstract void deleteFile();
}
