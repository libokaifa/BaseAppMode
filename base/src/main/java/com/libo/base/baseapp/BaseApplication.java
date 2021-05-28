package com.libo.base.baseapp;

import android.app.Application;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 9:52 上午
 * @describe:
 */
public class BaseApplication extends Application {
    public  static  Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
    }
}
