package com.yyds.log.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by 阿飞の小蝴蝶 on 2022/9/20
 * Describe:
 */
public class Base64Util {
    /**
     * 编码
     *
     * @param message 需编码的信息
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeWord(String message){
        try {
            return Base64.encodeToString(message.getBytes("utf-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码
     *
     * @param encodeWord 编码后的内容
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeWord(String encodeWord) {

        try {
            return new String(Base64.decode(encodeWord, Base64.NO_WRAP), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
