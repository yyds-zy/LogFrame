package com.yyds.logframe.manager;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/19
 * Describe:
 */
public interface OnRequestListener {

    void onFail(int errorCode);
    void onSuccess(String jsonData);
}
