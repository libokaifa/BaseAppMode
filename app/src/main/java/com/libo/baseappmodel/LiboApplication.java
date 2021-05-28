package com.libo.baseappmodel;

import android.os.Debug;

import com.kingja.loadsir.core.LoadSir;
import com.libo.base.baseapp.BaseApplication;
import com.libo.base.loadsir.CustomCallback;
import com.libo.base.loadsir.EmptyCallback;
import com.libo.base.loadsir.ErrorCallback;
import com.libo.base.loadsir.LoadingCallback;
import com.libo.base.loadsir.TimeoutCallback;
import com.libo.common.logger.LiboLoger;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.smtt.sdk.QbSdk;

import androidx.annotation.Nullable;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/28 3:32 下午
 * @describe:
 */
public  class LiboApplication  extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        initLoadSir();
        initLoger();
        initX5();
    }

    private void initX5() {

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LiboLoger.log("X5 onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    private void initLoger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //是否选择显示线程信息，默认为true
                .methodCount(0)         //方法数显示多少行，默认2行
                .methodOffset(7)        //隐藏方法内部调用到偏移量，默认5
             //   .logStrategy(customLog) //打印日志的策略，默认LogCat
                .tag("libotag")   //自定义TAG全部标签，默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }
}
