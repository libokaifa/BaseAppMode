package com.libo.base.baseapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.libo.base.util.AppManager;
import com.libo.base.util.ExceptionHelper;

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
        setApplication(this);
        initCrash();
    }
    public  void  initCrash(){
        ExceptionHelper.getInstance().init(true,this).register();

    }
    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull Application application) {


        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });
    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (mApplication == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return mApplication;
    }
}
