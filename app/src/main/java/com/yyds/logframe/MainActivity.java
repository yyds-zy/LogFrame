package com.yyds.logframe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yyds.log.util.CrashUtils;
import com.yyds.log.util.DeviceDetailInfo;
import com.yyds.log.util.JsonUtils;
import com.yyds.log.util.LogUtils;
import com.yyds.log.util.crash.CrashToFile;
import com.yyds.log.util.json.JsonToFile;
import com.yyds.log.util.log.LogToFile;
import com.yyds.logframe.app.AppApplication;
import com.yyds.logframe.manager.OnRequestListener;
import com.yyds.logframe.manager.UpLoadFileManager;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRequestListener {

    private TextView textView;
    private Button buttonLog,buttonCrash,buttonJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        buttonLog = findViewById(R.id.btn_log_upload);
        buttonCrash = findViewById(R.id.btn_crash_upload);
        buttonJson = findViewById(R.id.btn_json_upload);
        //输出设备信息
        String devicesInfo = DeviceDetailInfo.getDevicesInfo(AppApplication.application, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        textView.setText(devicesInfo);
        LogUtils.wtf(devicesInfo);
        LogUtils.v("MainActivity");
        LogUtils.v("111111111111");
        LogUtils.d("222222222222");
        LogUtils.i("333333333333");
        LogUtils.e("444444444444");
        LogUtils.w("555555555555");
        LogUtils.wtf("666666666666");

        JsonUtils.v("net data");

        CrashUtils.e("404");


        buttonLog.setOnClickListener(view -> {
            List<File> fileList = LogToFile.getInstance().getFileList();
            if (fileList != null) {
                for(File file : fileList){
                    UpLoadFileManager.getInstance().upLoadFile(file,MainActivity.this);
                }
            }
        });

        buttonCrash.setOnClickListener(view -> {
            List<File> crashList = CrashToFile.getInstance().getFileList();
            if (crashList != null) {
                for(File file : crashList){
                    UpLoadFileManager.getInstance().upLoadFile(file,MainActivity.this);
                }
            }
        });

        buttonJson.setOnClickListener(view -> {
            List<File> jsonList = JsonToFile.getInstance().getFileList();
            if (jsonList != null) {
                for(File file : jsonList){
                    UpLoadFileManager.getInstance().upLoadFile(file,MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onFail(int errorCode) {
        runOnUiThread(() -> textView.setText(errorCode+""));
    }

    @Override
    public void onSuccess(String jsonData) {
        runOnUiThread(() -> textView.setText(jsonData));
    }
}