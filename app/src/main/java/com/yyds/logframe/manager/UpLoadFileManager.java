package com.yyds.logframe.manager;

import com.yyds.logframe.util.OkHttpClientUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public class UpLoadFileManager {

    private static final int FILE_NOT_EXIST = 400;
    private static final int FILE_UPLOAD_FAIL = 405;
    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("file/*");
    private final OkHttpClient okHttpClient = OkHttpClientUtils.getClient();
    private static UpLoadFileManager instance;
    private static final String BASE_DEBUG_URL = "http://192.168.3.61:8089/Log/uploadLog";
    private UpLoadFileManager() {}

    public static UpLoadFileManager getInstance() {
        if (instance == null) {
            synchronized (UpLoadFileManager.class) {
                if (instance == null) {
                    instance = new UpLoadFileManager();
                }
            }
        }
        return instance;
    }

    /**
     * upLoadFile
     * @param file
     * @param listener
     */
    public void upLoadFile(File file,OnRequestListener listener) {
        if (file.exists()) {
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_FILE,file);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",file.getName(),requestBody)
                    .build();
            Request request = new Request.Builder()
                    .url(BASE_DEBUG_URL)
                    .post(body)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFail(FILE_UPLOAD_FAIL);
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    listener.onSuccess(response.body().string());
                }
            });
        } else {
            listener.onFail(FILE_NOT_EXIST);
        }
    }
}
