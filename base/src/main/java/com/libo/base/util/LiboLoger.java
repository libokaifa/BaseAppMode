package com.libo.base.util;

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
}
