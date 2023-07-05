package com.libo.base.util;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;


/**
 * author : LiBo
 * date : 2023/2/16 17:24
 * description :
 */
public class ExceptionHelper {

    private static volatile ExceptionHelper INSTANCE;
    private Boolean isDebug;
    private Application application;

    private ExceptionHelper() {
    }

    public static ExceptionHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (ExceptionHelper.class) {
                if (INSTANCE == null) {
                    synchronized (ExceptionHelper.class) {
                        INSTANCE = new ExceptionHelper();
                    }
                }
            }
        }
        return INSTANCE;
    }



    /**
     * 初始化默认异常捕获
     */
    public ExceptionHelper   init(Boolean isDebug,Application application) {
        this.isDebug=isDebug;
        this.application=application;
        return  this;
    }



    public void register() {

        //子线程异常拦截
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                pushError(e);
            }
        });

        //主线程异常拦截
        new Handler(Looper.getMainLooper()).post(() -> {
            while (true) {
                try {
                    Looper.loop();
                } catch (Throwable e) {
                 pushError(e);

                }
            }
        });



    }


    public  void  pushError(Throwable e){

            String  errName=e.getMessage();
            String pageName=ActivityUtils.getTopActivity().getClass().getName();
            if (true){
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
//                        if (LogUtil.IS_SHOW_LOG){
//                            Toast.makeText(application, "当前页面发生异常", Toast.LENGTH_LONG).show();
//                        }
                    }
                });
            }
//            LogUtil.e("YKErr","App错误上报==》"+" pageName: "+ pageName+" err info: " + errName+"：："+e.getMessage());
//            CrashReport.postCatchedException(new Throwable("App错误上报==》"+" pageName: "+ pageName+" err info: " + errName, e));

          

    }


}

