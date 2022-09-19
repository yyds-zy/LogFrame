package com.yyds.log.util.crash;

import android.content.Context;
import android.util.Log;
import com.yyds.log.util.base.BaseFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

public class CrashToFile extends BaseFile {

    public static final String savePath = "/Flying/Crash";
    private static CrashToFile instance;
    private CrashToFile(){}

    public static CrashToFile getInstance() {
        if (instance == null) {
            synchronized (CrashToFile.class) {
                if (instance == null) {
                    instance = new CrashToFile();
                }
            }
        }
        return instance;
    }

    @Override
    public void setFileSaveDays(int saveDays){
        FILE_SAVE_DAYS = saveDays;
    }

    @Override
    public int getFileSaveDays() {
        return FILE_SAVE_DAYS;
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    @Override
    public String getFilePath(Context context) {
        File dir = new File(context.getExternalCacheDir().getAbsoluteFile() + savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();//存当前app的文件夹里
    }

    @Override
    public void init(Context context) {
        mContent = context;
        logPath = getFilePath(context);//获得文件储存路径
    }

    /**
     * 文件是否存在
     * @param context
     * @return
     */
    @Override
    public boolean isExistsFile(Context context) {
        File dir = new File(context.getExternalCacheDir().getAbsoluteFile() + savePath);
        if (!dir.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public void e(String tag, String msg) {
        writeToFile(ERROR, tag, msg);
    }

    public void wtf(String tag, String msg) {
        writeToFile(WTF, tag, msg);
    }

    @Override
    public void writeToFile(char type, String tag, String msg) {
        logPath = getFilePath(mContent);
        if (null == logPath) {
            Log.e(TAG,"logPath == null ，未初始化CrashToFile");
            return;
        }

        String fileName = logPath + "/log_" + dateFormatFile.format(date) + ".log";//log日志名，使用时间命名，保证不重复  以天为单位  每天都有新的日志文件生成
        String segmentationLine = "**************************************************************";
        String log = segmentationLine + "\n" + dateFormatLog.format(new Date()) + " " + type + " " + tag + "\n" + "" + msg + "\n";//log日志内容，可以自行定制

        //如果父路径不存在
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<File> getFileList() {
        logPath = getFilePath(mContent);
        if (null == logPath) {
            Log.e(TAG,"logPath == null ，未初始化CrashToFile");
            return null;
        }
        mFileList.clear();
        File file = new File(logPath);
        File[] tempList = file.listFiles();
        long length = tempList.length;
        if (length == 0) {
            Log.e(TAG,"没有日志文件");
            return null;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String fileName = tempList[i].getName();
                if (fileName.endsWith(".log")) {
                    mFileList.add(tempList[i]);
                }
            }
        }
        return mFileList;
    }

    @Override
    public boolean isDeleteLogFile() {
        List<File> fileList = getFileList();
        if (fileList == null)
            return false;
        if (fileList.size() > getFileSaveDays()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteFile(){
        List<File> fileList = getFileList();
        if (fileList == null) return;
        if (fileList.size() > getFileSaveDays()) {
            for (File file : fileList) {
                if (file.exists()) {
                    String fileName = file.getName();
                    String format = dateFormatFile.format(date);
                    String fileNameTimes = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf(".log"));
                    if (!format.equals(fileNameTimes)) {
                        file.delete();
                    }
                }
            }
        }
    }
}
