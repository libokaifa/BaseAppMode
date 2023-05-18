package com.libo.base.util;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 4:39 下午
 * @describe:
 */
public  class LiboLoger {

    public  static void  log(String content){
        Logger.e(content);
    }

    public  static void  logJson(String content){
        Logger.json(content);
    }
    public static void  responseJson(String message){
        if (TextUtils.isEmpty(message))
            return;
        if (message.startsWith("{")||message.startsWith("[")){
            Logger.json(message);
        }else if (message.startsWith("<-- 200")){
            //e("Response_Date==》",message);
        }
    }
}
