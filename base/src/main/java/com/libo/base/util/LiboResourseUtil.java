package com.libo.base.util;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.libo.base.baseapp.BaseApplication;

import java.util.Observable;

import androidx.core.content.ContextCompat;

/**
 * /**
 *
 * @Author libo
 * @create 2021/5/31 10:24 上午
 * @describe:
 */
public  class LiboResourseUtil {
    public static String getString( int resId) {
        return  BaseApplication.mApplication.getString(resId);
    }
    public static int getColor( int resId) {
        return ContextCompat.getColor(BaseApplication.mApplication, resId);
    }

    public static Drawable getDrawable( int resId) {
        return ContextCompat.getDrawable(BaseApplication.mApplication, resId);
    }

    public static Object getArrayData(int resId,int key){
        TypedArray typedArray = BaseApplication.mApplication.getResources().obtainTypedArray(resId);
        return typedArray.getString(key);
    }
}
