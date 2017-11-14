package com.sz.quxin.addresschosedialog.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

public class Constant {

    private static final String TAG = "Constant";
    public static <T> T getPerson(String jsonString, Class cls) {
        T t = null;
        try {
            t = (T) JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            Log.e(TAG, "getPerson: 异常====" + e);
        }
        return t;
    }
    //public  static String address ="";//65535 string 常量池
}
