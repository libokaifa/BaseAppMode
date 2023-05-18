package com.libo.base.util;

import android.os.Handler;
import android.os.Looper;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;


/**
 * author: LiBO
 * time: 2021/12/3 11:03
 * Describe:
 */
public class PostUtil {
    private static final int DELAY_TIME = 1000;

    public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz) {
        postCallbackDelayed(loadService, clazz, DELAY_TIME);
    }

    public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz, long
            delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showCallback(clazz);
            }
        }, delay);
    }
    public static void postCallback(final LoadService loadService, final Class<? extends Callback> clazz) {
        if (loadService!=null){
            loadService.showCallback(clazz);
        }

    }
    public static void postSuccessDelayed(final LoadService loadService) {
        if (null==loadService)
            return;
        postSuccessDelayed(loadService, DELAY_TIME);
    }
    public static void postSuccess(final LoadService loadService) {
        if (null==loadService)
            return;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loadService.showSuccess();
            }
        });
    }
    public static void postSuccessDelayed(final LoadService loadService, long delay) {
        if (null==loadService)
            return;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showSuccess();
            }
        }, delay);
    }


}
